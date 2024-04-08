package com.stonewu.aifusion.module.system.convert.mail;

import cn.hutool.core.util.StrUtil;
import com.stonewu.aifusion.module.system.dal.dataobject.mail.MailAccountDO;
import org.dromara.hutool.extra.mail.MailAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailAccountConvert {

    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);

    default MailAccount convert(MailAccountDO account, String nickname) {
        String from = StrUtil.isNotEmpty(nickname) ? nickname + " <" + account.getMail() + ">" : account.getMail();
        return new MailAccount().setFrom(from).setAuth(true)
                .setUser(account.getUsername()).setPass(account.getPassword().toCharArray())
                .setHost(account.getHost()).setPort(account.getPort()).setSslEnable(account.getSslEnable());
    }

}
