package q3df.mil.service;


import q3df.mil.dto.dialog.DialogDto;

import java.util.List;

public interface DialogService {

//    DialogDto findById(Long id);

    List<DialogDto> findDialogsByUserId(Long id);

    DialogDto saveDialog(DialogDto dialogDto);

    void deleteById(Long id);


}
