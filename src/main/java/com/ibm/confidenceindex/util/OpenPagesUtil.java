package com.ibm.confidenceindex.util;

import com.ibm.openpages.api.metadata.ITypeDefinition;
import com.ibm.openpages.api.metadata.Id;
import com.ibm.openpages.api.query.IQuery;
import com.ibm.openpages.api.query.IResultSetRow;
import com.ibm.openpages.api.query.ITabularResultSet;
import com.ibm.openpages.api.resource.*;
import com.ibm.openpages.api.service.IQueryService;
import com.ibm.openpages.api.service.IResourceService;
import com.ibm.openpages.api.service.IServiceFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpenPagesUtil {

    public static IStringField getStringField(IGRCObject object, String name) {
        return (IStringField) object.getField(name);
    }

    public static IIntegerField getIntegerField(IGRCObject object, String name) {
        return (IIntegerField) object.getField(name);
    }

    public static List<Id> processQuery(final IServiceFactory serviceFactory, final String querySelect) {
        final IQueryService queryService = serviceFactory.createQueryService();
        final IQuery query = queryService.buildQuery(querySelect);
        final ITabularResultSet resultSet = query.fetchRows(0);
        Iterator<IResultSetRow> resultSetIterator = resultSet.iterator();

        final List<Id> result = new ArrayList<Id>();

        while (resultSetIterator.hasNext()) {
            IResultSetRow row = resultSetIterator.next();
            Iterator<IField> rowIterator = row.iterator();

            while (rowIterator.hasNext()) {
                IField field = rowIterator.next();
                if (field instanceof IIdField) {
                    result.add(((IIdField) field).getId());
                }
            }
        }

        return result;
    }

    public static IGRCObject lookupResource(IServiceFactory factory, Id id) {
        final IResourceService service = factory.createResourceService();

        return service.getGRCObject(id);
    }

    public static boolean isModelUseCase(ITypeDefinition type) {
        return "ModelUseCase".equals(type.getName());
    }

}
