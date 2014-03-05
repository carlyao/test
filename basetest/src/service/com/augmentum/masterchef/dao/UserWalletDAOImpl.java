package com.augmentum.masterchef.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Component;

import com.augmentum.masterchef.vo.WalletVo;


@Component
public class UserWalletDAOImpl extends UserWalletDAOBaseImpl
    implements UserWalletDAO {
    private static Log log = LogFactory.getLog(UserWalletDAOImpl.class);

	@Override
	public WalletVo findWalletByUserId(long userId) {
		String sql = "select distinct b.userId as userId,(select c.value from UserWallet c where c.userId= ? and c.walletId = 101) as coin, (select c.value from UserWallet c where c.userId= ? and c.walletId = 1) as cash from UserWallet b where b.userId = ? ";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setLong(0, userId);
		query.setLong(1, userId);
		query.setLong(2, userId);
		query.addScalar("userId",  LongType.INSTANCE);
		query.addScalar("coin", IntegerType.INSTANCE);
		query.addScalar("cash", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers
				.aliasToBean(WalletVo.class));
		List<WalletVo> walletVos = new ArrayList<WalletVo>();
		walletVos = query.list();
		if(walletVos.size() >0){
			return walletVos.get(0);
		}
		return null;
	}
}
