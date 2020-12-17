package com.aws.simplestorageservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class FileMetaDataDto {

    @JsonProperty("bucket_name")
    private String bucketName;

    @JsonProperty("file_owner")
    private String fileOwner;

    @JsonProperty("file_key")
    private String fileKey;

    @JsonProperty("last_modified_date")
    private Date lastModifiedDate;

    @JsonProperty("file_size")
    private Long fileSize;
}
