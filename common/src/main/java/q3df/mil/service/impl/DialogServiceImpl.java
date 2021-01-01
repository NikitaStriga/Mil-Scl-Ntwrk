package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.dialog.DialogDto;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.mapper.dialog.DialogMapper;
import q3df.mil.repository.DialogRepository;

import java.util.List;
import java.util.stream.Collectors;

import static q3df.mil.exception.ExceptionConstants.DIALOG_NF;

@Service
public class DialogServiceImpl implements q3df.mil.service.DialogService {

    private final DialogRepository dialogRepository;
    private final DialogMapper dialogMapper;

    @Autowired
    public DialogServiceImpl(DialogRepository dialogRepository, DialogMapper dialogMapper) {
        this.dialogRepository = dialogRepository;
        this.dialogMapper = dialogMapper;
    }


    /**
     * find list of dialogs by user id
     * @param id user id
     * @return list of user dialogs
     */
    @Override
    public List<DialogDto> findDialogsByUserId(Long id){
        return dialogRepository
                .findDialogsByUserId(id)
                .stream()
                .map(dialogMapper::toDto)
                .collect(Collectors.toList());

    }


    //this method is not used cuz all dialogs create in message repostory
//    @org.springframework.transaction.annotation.Transactional
//    public DialogDto saveDialog(DialogDto dialogDto) {
//        Dialog dialog = dialogMapper.fromDto(dialogDto);
//        Dialog savedDialog = dialogRepository.save(dialog);
//        return dialogMapper.toDto(savedDialog);
//    }


    /**
     * delete dialog
     * @param id dialog id
     */
    @Override
    public void deleteById(Long id) {
        try{
            dialogRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new DialogNotFoundException(DIALOG_NF + id);
        }
    }


}
