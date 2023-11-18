package com.jewelry.file.entity;

import com.jewelry.file.dto.FileDto;
import com.jewelry.file.dto.FileResponseDto;
import com.jewelry.file.dto.QFileResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

import static com.jewelry.file.entity.QFile.file;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public List<FileResponseDto> getFiles(FileDto fileDto) {
    return queryFactory
        .select(new QFileResponseDto(
            file.fileNo, file.refNo, file.refInfo
            , file.filePath, file.originNm, file.fileNm
            , file.fileOrd, file.fileExt, file.fileSize)
        )
        .from(file)
        .where(
            file.refNo.eq(fileDto.getRefNo())
                .and(file.refInfo.eq(fileDto.getRefInfo()))
                .and(file.delYn.eq("N"))
        )
        .orderBy(
            file.fileOrd.asc()
        )
        .fetch();
  }

  @Override
  public long updateToDelete(final FileDto fileDto){
    return queryFactory
        .update(file)
        .set(file.updtDt, LocalDateTime.now())
        .set(file.updtId, fileDto.getUpdtId())
        .set(file.delYn, "Y")
        .where(
            file.refNo.eq(fileDto.getRefNo())
                .and(file.refInfo.eq(fileDto.getRefInfo()))
        )
        .execute();

  }

  @Override
  public FileResponseDto getFileByRef(FileDto fileDto) {
    return queryFactory
        .select(new QFileResponseDto(
            file.fileNo, file.refNo, file.refInfo
            , file.filePath, file.originNm, file.fileNm
            , file.fileOrd, file.fileExt, file.fileSize)
        )
        .from(file)
        .where(
            file.refNo.eq(fileDto.getRefNo())
                .and(file.refInfo.eq(fileDto.getRefInfo()))
                .and(file.fileOrd.eq(fileDto.getFileOrd()))
                .and(file.delYn.eq("N"))
        )
        .fetchOne();
  }
}
