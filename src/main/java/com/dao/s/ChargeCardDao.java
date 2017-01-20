package com.dao.s;

import com.entity.s.ChangeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Created by jianggk on 2016/10/25.
 */
@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "ChargeCard", path = "cc")
public interface ChargeCardDao extends JpaRepository<ChangeCard, String> {
    @RestResource(path = "findByDeviceNumber", rel = "findByDeviceNumber")
    ChangeCard findByDeviceNumber(@Param("dn") String deviceNumber);
    @RestResource(path = "findAll", rel = "findAll")
    List<ChangeCard> findAll();
}

//{"alps":
//        {
//            "version":"1.0",
//            "descriptors":[
//                                {
//                                    "id":"changeCard-representation",
//                                    "href":"http://127.0.0.1/profile/cc",
//                                    "descriptors":[
//                                                    {"name":"familyName","type":"SEMANTIC"},
//                                                    {"name":"sex","type":"SEMANTIC"},
//                                                    {"name":"managerFamilyName","type":"SEMANTIC"},
//                                                    {"name":"managerTele","type":"SEMANTIC"}
//                                    ]
//                                },
//                                {
//                                                "id":"get-cc",
//                                                "name":"cc",
//                                                "type":"SAFE",
//                                                "rt":"#changeCard-representation",
//                                                "descriptors":[
//                                                                {"name":"page","doc":{"value":"The page to return.","format":"TEXT"},"type":"SEMANTIC"},
//                                                                {"name":"size","doc":{"value":"The size of the page to return.","format":"TEXT"},"type":"SEMANTIC"},
//                                                                {"name":"sort","doc":{"value":"The sorting criteria to use to calculate the content of the page.","format":"TEXT"},"type":"SEMANTIC"}
//                                                ]
//                                },
//                                {"id":"create-cc","name":"cc","type":"UNSAFE","rt":"#changeCard-representation"},
//                                {"id":"update-changeCard","name":"changeCard","type":"IDEMPOTENT","rt":"#changeCard-representation"},
//                                {"id":"get-changeCard","name":"changeCard","type":"SAFE","rt":"#changeCard-representation"},
//                                {"id":"delete-changeCard","name":"changeCard","type":"IDEMPOTENT","rt":"#changeCard-representation"},
//                                {"id":"patch-changeCard","name":"changeCard","type":"UNSAFE","rt":"#changeCard-representation"},
//                                {"name":"findByDeviceNumber","type":"SAFE","descriptors":[{"name":"dn","type":"SEMANTIC"}]}
//        ]
//    }
//}
