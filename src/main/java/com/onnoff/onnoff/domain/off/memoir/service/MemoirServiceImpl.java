package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.handler.MemoirHandler;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.Emoticon;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirAnswerRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.EmoticonRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirQuestionRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirRepository;
import com.onnoff.onnoff.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;
    private final MemoirAnswerRepository memoirAnswerRepository;
    private final MemoirQuestionRepository memoirQuestionRepository;
    private final EmoticonRepository emoticonRepository;

    @Override
    @Transactional
    public Memoir writeMemoir(MemoirRequestDTO.MemoirWriteDTO request) {
        User user = UserContext.getUser();
        if (memoirRepository.findByUserAndDate(user, request.getDate()).isPresent()) {
            throw new MemoirHandler(ErrorStatus.MEMOIR_EXIST);
        }

        Memoir newMemoir = Memoir.builder()
                .date(request.getDate())
                .emoticon(emoticonRepository.findById(request.getEmoticonId()).orElseThrow(() -> new MemoirHandler(ErrorStatus.EMOTICON_NOT_FOUND)))
                .isBookmarked(false)
                .build();

        List<MemoirAnswer> newMemoirAnswerList = request.getMemoirAnswerList().stream()
                .map(memoirAnswer -> MemoirAnswer.builder()
                        .answer(memoirAnswer.getAnswer().trim())
                        .memoirQuestion(memoirQuestionRepository.findById(memoirAnswer.getQuestionId()).orElseThrow(() -> new MemoirHandler(ErrorStatus.QUESTION_NOT_FOUND)))
                        .memoir(newMemoir)
                        .build())
                .collect(Collectors.toList());

        newMemoir.setUser(user);
        newMemoir.setMemoirAnswerList(newMemoirAnswerList);

        return memoirRepository.save(newMemoir);
    }

    @Override
    @Transactional(readOnly = true)
    public Memoir getMemoirPreview(LocalDate date) {
        User user = UserContext.getUser();
        return memoirRepository.findByUserAndDate(user, date).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Memoir getMemoir(LocalDate date) {
        User user = UserContext.getUser();
        return memoirRepository.findByUserAndDate(user, date).orElseThrow(() -> new MemoirHandler(ErrorStatus.MEMOIR_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Memoir> getBookmarkedMemoir(Integer pageNumber) {
        User user = UserContext.getUser();
        return memoirRepository.findByUserAndIsBookmarkedOrderByDateDesc(user, true, PageRequest.of(pageNumber, 10));
    }

    @Override
    @Transactional
    public Memoir modifyMemoir(Long memoirId, MemoirRequestDTO.MemoirUpdateDTO request) {
        Memoir memoir = memoirRepository.findById(memoirId).orElseThrow(() -> new MemoirHandler(ErrorStatus.MEMOIR_NOT_FOUND));

        memoir.setEmoticon(emoticonRepository.findById(request.getEmoticonId()).orElseThrow(() -> new MemoirHandler(ErrorStatus.EMOTICON_NOT_FOUND)));

        for (MemoirRequestDTO.MemoirUpdateAnswerDTO memoirAnswer : request.getMemoirAnswerList()) {
            MemoirAnswer findMemoirAnswer = memoirAnswerRepository.findById(memoirAnswer.getAnswerId()).orElseThrow(() -> new MemoirHandler(ErrorStatus.ANSWER_NOT_FOUND));
            if (findMemoirAnswer.getMemoir() != memoir) {
                throw new MemoirHandler(ErrorStatus.ANSWER_BAD_MATCH);
            }
            findMemoirAnswer.setAnswer(memoirAnswer.getAnswer().trim());
        }

        return memoir;
    }

    @Override
    @Transactional
    public Memoir bookmarkMemoir(Long memoirId) {
        Memoir memoir = memoirRepository.findById(memoirId).orElseThrow(() -> new MemoirHandler(ErrorStatus.MEMOIR_NOT_FOUND));
        memoir.setIsBookmarked(memoir.getIsBookmarked().equals(false));

        return memoir;
    }

    @Override
    @Transactional
    public Long deleteMemoir(Long memoirId) {
        Memoir memoir = memoirRepository.findById(memoirId).orElseThrow(() -> new MemoirHandler(ErrorStatus.MEMOIR_NOT_FOUND));
        memoirRepository.delete(memoir);

        return memoir.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoirQuestion> getMemoirQuestion() {
        return memoirQuestionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Emoticon> getEmoticon() {
        return emoticonRepository.findAll();
    }
}
