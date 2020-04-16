package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.PropertyValueDoMapper;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.PropertyDo;
import com.cxp.tmall.database.PropertyValueDo;
import com.cxp.tmall.database.PropertyValueDoExample;
import com.cxp.tmall.service.PropertyService;
import com.cxp.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueDoMapper propertyValueDoMapper;
    @Autowired
    PropertyService propertyService;


    @Override
    public void init(ProductDo p) {

        List<PropertyDo> pts = propertyService.list(p.getCid());

        for (PropertyDo pt: pts) {
            PropertyValueDo pv = get(pt.getId(),p.getId());
            if(null==pv){
                pv = new PropertyValueDo ();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueDoMapper.insert(pv);
            }
        }

    }

    @Override
    public void update(PropertyValueDo pv) {
        propertyValueDoMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValueDo get(int ptid, int pid) {
        PropertyValueDoExample example = new PropertyValueDoExample ();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValueDo> pvs= propertyValueDoMapper.selectByExample(example);
        if (pvs.isEmpty())
            return null;
        return pvs.get(0);
    }

    @Override
    public List<PropertyValueDo> list(int pid) {
        PropertyValueDoExample example = new PropertyValueDoExample ();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValueDo> result = propertyValueDoMapper.selectByExample(example);
        for (PropertyValueDo pv : result) {
            PropertyDo property = propertyService.get(pv.getPtid());
            pv.setPropertyDo (property);
        }
        return result;
    }
//    @Override
//    public void init (ProductDo p) {
//    List<PropertyDo> pts = propertyService.list ( p.getCid () );
//
//    for(PropertyDo pt :pts){
//        PropertyValueDo pv= get ( pt.getId (),p.getId () );
//        if(null==pv){
//            pv=new PropertyValueDo ();
//            pv.setPid ( p.getId () );
//            pv.setPtid ( pt.getId () );
//            propertyValueDoMapper.insert ( pv );
//        }
//    }
//    }
//
//    @Override
//    public void update (PropertyValueDo pv) {
//        propertyValueDoMapper.updateByPrimaryKeySelective ( pv );
//    }
//
//    @Override
//    public PropertyValueDo get (int ptid, int pid) {
//        PropertyValueDoExample propertyValueDoExample = new PropertyValueDoExample ();
//        propertyValueDoExample.createCriteria ().andPtidEqualTo(ptid).andPidEqualTo(pid);
//        List<PropertyValueDo> pvs = propertyValueDoMapper.selectByExample ( propertyValueDoExample );
//        if(pvs.isEmpty ()){
//            return null;
//        }
//        return pvs.get ( 0 );
//    }
//
//    @Override
//    public List<PropertyValueDo> list (int pid) {
//        PropertyValueDoExample propertyValueDoExample = new PropertyValueDoExample ();
//        propertyValueDoExample.createCriteria ().andPidEqualTo ( pid );
//        List<PropertyValueDo> result =propertyValueDoMapper.selectByExample ( propertyValueDoExample );
//        for (PropertyValueDo pv :result){
//            PropertyDo propertyDo = propertyService.get ( pv.getPtid () );
//            pv.setPropertyDo ( propertyDo );
//        }
//        return result;
//    }
}
