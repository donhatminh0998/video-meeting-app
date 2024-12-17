package com.example.video_meeting_app.message.repository;

import com.example.video_meeting_app.message.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}