package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.domain.off.memoir.converter.MemoirConverter;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirAnswerRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirQuestionRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirRepository memoirRepository;
    private final MemoirAnswerRepository memoirAnswerRepository;
    private final MemoirQuestionRepository memoirQuestionRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemoirQuestion> getMemoirQuestion(Long userId) {
        return memoirQuestionRepository.findAll();
    }

    @Override
    @Transactional
    public Memoir writeMemoir(MemoirRequestDTO.WriteDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        if (memoirRepository.findByUserAndDate(user, request.getDate()).isPresent()) {
            throw new GeneralException(ErrorStatus.MEMOIR_EXIST);
        }

        Memoir newMemoir = MemoirConverter.toMemoir(request);

        List<MemoirAnswer> newMemoirAnswerList = request.getMemoirAnswerList().stream()
                .map(memoirAnswer -> MemoirAnswer.builder()
                        .answer(memoirAnswer.getAnswer())
                        .memoirQuestion(memoirQuestionRepository.findById(memoirAnswer.getQuestionId()).orElseThrow(() -> new GeneralException(ErrorStatus.QUESTION_NOT_FOUND)))
                        .memoir(newMemoir)
                        .build())
                .collect(Collectors.toList());

        newMemoir.setUser(user);
        newMemoir.setMemoirAnswerList(newMemoirAnswerList);

        return memoirRepository.save(newMemoir);
    }

    @Override
    @Transactional(readOnly = true)
    public Memoir getMemoir(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(()  -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return memoirRepository.findByUserAndDate(user, date).orElse(null);
    }

    @Override
    @Transactional
    public Memoir updateMemoir(MemoirRequestDTO.UpdateDTO request) {
        Memoir memoir = memoirRepository.findById(request.getMemoirId()).orElseThrow(() -> new GeneralException(ErrorStatus.MEMOIR_NOT_FOUND));

        memoir.setIcon(request.getIcon());
        memoir.setIsBookmarked(request.getIsBookmarked());

        for (MemoirRequestDTO.UpdateAnswerDTO memoirAnswer: request.getMemoirAnswerList()) {
            MemoirAnswer findMemoirAnswer = memoirAnswerRepository.findById(memoirAnswer.getAnswerId()).orElseThrow(() -> new GeneralException(ErrorStatus.ANSWER_NOT_FOUND));
            if (findMemoirAnswer.getMemoir() != memoir) {
                throw new GeneralException(ErrorStatus.ANSWER_BAD_MATCH);
            }
            findMemoirAnswer.setAnswer(memoirAnswer.getAnswer());
        }

        return memoirRepository.save(memoir);
    }

    @Override
    @Transactional
    public Memoir deleteMemoir(Long memoirId) {
        Memoir memoir = memoirRepository.findById(memoirId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMOIR_NOT_FOUND));
        memoirRepository.delete(memoir);
        return memoir;
    }
}
