package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFile {

    @Column(name = "ITEMS_NO")
    private String apvNo;

    @Id
    @Column(name = "FILE_NO")
    private String fileNo;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "SAVED_FILE_NAME")
    private String savedFileName;

}
