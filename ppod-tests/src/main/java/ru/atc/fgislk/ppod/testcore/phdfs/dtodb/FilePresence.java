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
public class FilePresence implements FromResultSet {
    private Long id;
    private String fileId;
    private String node;
    private String filePath;
    private String status;
    private Timestamp createdAt;
    private Timestamp uploadedAt;
    private Boolean fakeNode;
    private String bucket;

    public FilePresence(ResultSet resultSet) throws SQLException {
        super();
        id = resultSet.getLong("id");
        fileId = resultSet.getString("file_id");
        node = resultSet.getString("node");
        filePath = resultSet.getString("file_path");
        status = resultSet.getString("status");
        createdAt = resultSet.getTimestamp("created_at");
        uploadedAt = resultSet.getTimestamp("uploaded_at");
        fakeNode = resultSet.getBoolean("fake_node");
        bucket = resultSet.getString("bucket");
    }

    public FilePresence() {
        super();
    }

    @Override
    public void fromResultSet(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("id");
        fileId = resultSet.getString("file_id");
        node = resultSet.getString("node");
        filePath = resultSet.getString("file_path");
        status = resultSet.getString("status");
        createdAt = resultSet.getTimestamp("created_at");
        uploadedAt = resultSet.getTimestamp("uploaded_at");
        fakeNode = resultSet.getBoolean("fake_node");
        bucket = resultSet.getString("bucket");
    }
}
