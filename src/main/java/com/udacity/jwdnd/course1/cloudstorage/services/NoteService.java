package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getUserNotes(String username) {
        User user = userMapper.getUser(username);
        return noteMapper.getUserNotes(user.getUserId());
    }

    public int addNote(Note note, String username) {
        User user = userMapper.getUser(username);
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public boolean isNoteAlreadyExist(Integer noteId) {
        return noteMapper.getNote(noteId) != null;
    }

    public boolean deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }
}
