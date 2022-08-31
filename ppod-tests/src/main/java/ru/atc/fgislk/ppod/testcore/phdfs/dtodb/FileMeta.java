package ru.atc.fgislk.ppod.testcore.phdfs.dtodb;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.atc.fgislk.shared.testcomponents.postgresql.entity.FromResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@Accessors
public class FileMeta implements FromResultSet {

  private String id;
  private String containerCode;
  private String code;
  private String fileType;
  private String originalFileName;
  private long size;
  private String md5Sum;
  private Timestamp removeBefore;
  private String expired;
  private String contentType;
  private String source;
  private Timestamp createdAt;
  private Timestamp uploadedAt;
  private String fileAttributes;

  public FileMeta(ResultSet resultSet) throws SQLException {
    super();
    id = resultSet.getString("id");
    containerCode = resultSet.getString("container_code");
    code = resultSet.getString("code");
    fileType = resultSet.getString("file_type");
    originalFileName = resultSet.getString("original_file_name");
    size = resultSet.getLong("size");
    md5Sum = resultSet.getString("md5sum");
    removeBefore = resultSet.getTimestamp("remove_before");
    expired = resultSet.getString("expired");
    contentType = resultSet.getString("content_type");
    source = resultSet.getString("source");
    createdAt = resultSet.getTimestamp("created_at");
    uploadedAt = resultSet.getTimestamp("uploaded_at");
    fileAttributes = resultSet.getString("file_attributes");
  }

  public FileMeta() {
    super();
  }

  @Override
  public void fromResultSet(ResultSet resultSet) throws SQLException {
    id = resultSet.getString("id");
    containerCode = resultSet.getString("container_code");
    code = resultSet.getString("code");
    fileType = resultSet.getString("file_type");
    originalFileName = resultSet.getString("original_file_name");
    size = resultSet.getLong("size");
    md5Sum = resultSet.getString("md5sum");
    removeBefore = resultSet.getTimestamp("remove_before");
    expired = resultSet.getString("expired");
    contentType = resultSet.getString("content_type");
    source = resultSet.getString("source");
    createdAt = resultSet.getTimestamp("created_at");
    uploadedAt = resultSet.getTimestamp("uploaded_at");
    fileAttributes = resultSet.getString("file_attributes");
  }
}
