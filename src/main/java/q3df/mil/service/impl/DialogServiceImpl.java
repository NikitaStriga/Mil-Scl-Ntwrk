package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q3df.mil.dto.dialog_message_dto.DialogDto;
import q3df.mil.mapper.dialog_message_mappers.DialogMapper;
import q3df.mil.repository.DialogRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DialogServiceImpl implements q3df.mil.service.DialogService {

    private final DialogRepository dialogRepository;
    private final DialogMapper dialogMapper;

    @Autowired
    public DialogServiceImpl(DialogRepository dialogRepository, DialogMapper dialogMapper) {
        this.dialogRepository = dialogRepository;
        this.dialogMapper = dialogMapper;
    }

    @Transactional
    public List<DialogDto> findDialogsByUserId(Long id){
        return dialogRepository.findDialogsByUserId(id).stream().map(dialogMapper::toDto).collect(Collectors.toList());

    }


}
