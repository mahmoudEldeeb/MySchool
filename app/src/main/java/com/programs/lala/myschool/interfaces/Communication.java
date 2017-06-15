package com.programs.lala.myschool.interfaces;

import com.programs.lala.myschool.modeld.AccountModel;
import com.programs.lala.myschool.modeld.MessageModel;
import com.programs.lala.myschool.modeld.PostModel;

/**
 * Created by melde on 6/13/2017.
 */

public interface Communication {
    void onClickPost(PostModel model);

    void onClickAccount(AccountModel model);

    void onClickMessage(MessageModel model);
}
