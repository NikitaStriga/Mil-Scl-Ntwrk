package q3df.mil.service;


import q3df.mil.dto.dialog.DialogDto;

import java.util.List;

public interface DialogService {

    List<DialogDto> findDialogsByUserId(Long id);

    void deleteById(Long id);


}
