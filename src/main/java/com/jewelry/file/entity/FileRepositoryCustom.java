package com.jewelry.file.entity;

import com.jewelry.file.dto.FileDto;
import com.jewelry.file.dto.FileResponseDto;

import java.util.List;

public interface FileRepositoryCustom {

  List<FileResponseDto> getFiles(final FileDto fileDto);

  long updateToDelete(final FileDto fileDto);

  FileResponseDto getFileByRef(final FileDto fileDto);


}
