package kr.dklog.mapper;

import kr.dklog.dto.StudentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface StudentMapper {

    Optional<StudentDto> findById(Long studentId);

    Optional<StudentDto> findByName(String name);

    Optional<StudentDto> findByPhoneNumber(String phoneNumber);

    Long updateStudent(StudentDto studentDto);

    Optional<StudentDto> findByGithubUsername(String githubUsername);
}
