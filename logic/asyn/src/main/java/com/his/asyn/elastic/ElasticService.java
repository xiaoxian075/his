package com.his.asyn.elastic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.his.asyn.elastic.diagnose.ElasticDiagnose;
import com.his.asyn.elastic.drug.ElasticDrug;
import com.his.asyn.elastic.item.ElasticItem;
import com.his.asyn.elastic.patient.ElasticPatient;
import com.his.asyn.third.PinYin;
import com.his.asyn.third.WuBi;
import com.his.common.define.EnumSessionError;
import com.his.common.mq.elastic.ElasticDefine;
import com.his.common.mq.elastic.ElasticNode;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;

@Component
public class ElasticService {
	
	@Autowired
	private PinYin pinyin;
	
	@Autowired
	private WuBi wubi;
	
	/**
	 * 查询
	 */
	public Return<List<PywuVo>> testSearch(int page, int size, int type, String word, long extra) {
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		List<PywuVo> voList = eastic.testSearch(page, size, word, extra);
		if (voList == null) {
			return Constant.ret(EnumSessionError.NO_DATA_ERROR);
		}
		
		return Constant.ret(voList);
	}
	
	/**
	 * 查询
	 */
	public Return<List<Long>> allSearch(int page, int size, int type, String word, long extra) {
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}

		List<Long> ids = eastic.search(page, size, word, extra);
		if (ids == null) {
			return Constant.ret(EnumSessionError.NO_DATA_ERROR);
		}
		
		return Constant.ret(ids);
	}
	
	
	/**
	 * 批量添加
	 * @param type
	 * @param word
	 * @return
	 */
	public Return<List<PywuVo>> addList(int type, List<ElasticNode> listData) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		List<PywuVo> listVo = new ArrayList<PywuVo>();
		for (ElasticNode node : listData) {
			String word = node.getWord();
			String py = pinyin.getPinyinSingle(word);
			String wb = wubi.getWubiSingle(word);
			if (py!=null) {
				listVo.add(new PywuVo(node.getId(), py, wb, node.getClinicId()));
			}
		}
		
		if (listVo.size() > 0 && !eastic.addList(listVo)) {
			return Constant.ret(EnumSessionError.EASTIC_ADDLIST_ERROR);
		}
		
		return Constant.ret(listVo);
	}

	/**
	 * 添加
	 * @param type
	 * @param word
	 * @return
	 */
	public Return<PywuVo> add(long id, int type, String word, long extra) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		String py = pinyin.getPinyinSingle(word);
		String wb = wubi.getWubiSingle(word);
		if (py==null) {
			return Constant.ret(EnumSessionError.PINYIN_CHANGE_ERROR);
		}
		PywuVo vo = new PywuVo(id, py, wb, extra);
		if (!eastic.add(vo)) {
			return Constant.ret(EnumSessionError.EASTIC_ADD_ERROR);
		}
		
		return Constant.ret(vo);
	}
	
	/**
	 * 修改
	 * @param type
	 * @param word
	 * @return
	 */
	public Return<PywuVo> update(long id, int type, String word, long extra) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}

		String py = pinyin.getPinyinSingle(word);
		String wb = wubi.getWubiSingle(word);
		if (py==null) {
			return Constant.ret(EnumSessionError.PINYIN_CHANGE_ERROR);
		}
		PywuVo vo = new PywuVo(id, py, wb, extra);
		if (!eastic.addOrUpdate(vo)) {
			return Constant.ret(EnumSessionError.EASTIC_UPDATE_ERROR);
		}
		
		return Constant.ret(vo);
	}
	
	/**
	 * 	添加或修改
	 * @param type
	 * @param word
	 * @return
	 */
	public Return<PywuVo> addOrupdate(long id, int type, String word, long extra) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		String py = pinyin.getPinyinSingle(word);
		String wb = wubi.getWubiSingle(word);
		if (py==null) {
			return Constant.ret(EnumSessionError.PINYIN_CHANGE_ERROR);
		}
		
		PywuVo vo = new PywuVo(id, py, wb, extra);
		if (!eastic.addOrUpdate(vo)) {
			return Constant.ret(EnumSessionError.EASTIC_ADDORUPDATE_ERROR);
		}
		
		return Constant.ret(vo);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Return<PywuVo> delete(long id, int type) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		PywuVo vo = eastic.delete(id);
		if (vo == null) {
			return Constant.ret(EnumSessionError.EASTIC_DELETE_ERROR);
		}
		
		return Constant.ret(vo);
	}
	
	/**
	 * 全部删除
	 * @param id
	 * @return
	 */
	public Return<Object> deleteAll(int type) {
		
		IElastic eastic = getElastic(type);
		if (eastic == null) {
			return Constant.ret(EnumSessionError.PARAM_ERROR);
		}
		
		if (!eastic.deleteAll()) {
			return Constant.ret(EnumSessionError.EASTIC_DELETEALL_ERROR);
		}
		
		return Constant.ret();
	}
	
	
	@Resource
	private ElasticDrug elasticDrug;
	
	@Resource
	private ElasticItem elasticItem;
	
	@Resource
	private ElasticDiagnose elasticDiagnose;
	
	@Resource
	private ElasticPatient elasticPatient;
	
	private IElastic getElastic(int type) {
		IElastic iElastic = null;
		switch(type) {
		case ElasticDefine.PYWB_DATA_DRUG:
			iElastic = elasticDrug;
			break;
		case ElasticDefine.PYWB_DATA_ITEM:
			iElastic = elasticItem;
			break;
		case ElasticDefine.PYWB_DATA_DIAGNOSE:
			iElastic = elasticDiagnose;
			break;
		case ElasticDefine.PYWB_DATA_PATIENT:
			iElastic = elasticPatient;
			break;
		}
		return iElastic;
	}
}
