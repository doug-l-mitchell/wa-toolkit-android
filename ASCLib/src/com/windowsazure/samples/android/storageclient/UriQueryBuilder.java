package com.windowsazure.samples.android.storageclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

final class UriQueryBuilder
{

    UriQueryBuilder()
    {
        m_Parameters = new HashMap();
    }

    public void add(String parameterKey, String parameterValue)
        throws IllegalArgumentException, StorageException
    {
        if(Utility.isNullOrEmpty(parameterKey))
        {
            throw new IllegalArgumentException("Cannot encode a query parameter with a null or empty key");
        } 
        else
        {
            insertKeyValue(parameterKey, parameterValue);
        }
    }

    public URI addToURI(URI originalUri)
        throws URISyntaxException, StorageException
    {
        String originalRawQuery = originalUri.getRawQuery();
        String originalRawFragment = originalUri.getRawFragment();
        String originalUriAsString = originalUri.resolve(originalUri).toASCIIString();
        HashMap queryArguments = PathUtility.parseQueryString(originalRawQuery);
        for(Iterator queryArgumentIterator = queryArguments.entrySet().iterator(); queryArgumentIterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)queryArgumentIterator.next();
            String argumentValues[] = (String[])entry.getValue();
            int argumentValuesLength = argumentValues.length;
            int argumentValueIndex = 0;
            while (argumentValueIndex < argumentValuesLength) 
            {
                String argumentValue = argumentValues[argumentValueIndex];
                insertKeyValue((String)entry.getKey(), argumentValue);
                argumentValueIndex++;
            }
        }

        StringBuilder stringbuilder = new StringBuilder();
        if(Utility.isNullOrEmpty(originalRawQuery) && !Utility.isNullOrEmpty(originalRawFragment))
        {
            int i = originalUriAsString.indexOf('#');
            stringbuilder.append(originalUriAsString.substring(0, i));
        } else
        if(!Utility.isNullOrEmpty(originalRawQuery))
        {
            int j = originalUriAsString.indexOf('?');
            stringbuilder.append(originalUriAsString.substring(0, j));
        } else
        {
            stringbuilder.append(originalUriAsString);
            if(originalUri.getRawPath().length() <= 0)
                stringbuilder.append("/");
        }
        String s3 = toString();
        if(s3.length() > 0)
        {
            stringbuilder.append("?");
            stringbuilder.append(s3);
        }
        if(!Utility.isNullOrEmpty(originalRawFragment))
        {
            stringbuilder.append("#");
            stringbuilder.append(originalRawFragment);
        }
        return new URI(stringbuilder.toString());
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        Boolean boolean1 = Boolean.valueOf(true);
        Iterator iterator = m_Parameters.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(m_Parameters.get(s) != null)
            {
                Iterator iterator1 = ((ArrayList)m_Parameters.get(s)).iterator();
                while(iterator1.hasNext()) 
                {
                    String s1 = (String)iterator1.next();
                    if(boolean1.booleanValue())
                        boolean1 = Boolean.valueOf(false);
                    else
                        stringbuilder.append("&");
                    stringbuilder.append(String.format("%s=%s", new Object[] {
                        s, s1
                    }));
                }
            }
        } while(true);
        return stringbuilder.toString();
    }

    private void insertKeyValue(String s, String s1)
        throws StorageException
    {
        if(s1 != null)
            s1 = Utility.safeEncode(s1);
        s = Utility.safeEncode(s);
        ArrayList arraylist = (ArrayList)m_Parameters.get(s);
        if(arraylist == null)
        {
            arraylist = new ArrayList();
            arraylist.add(s1);
            m_Parameters.put(s, arraylist);
        } else
        if(!arraylist.contains(s1))
            arraylist.add(s1);
    }

    private HashMap m_Parameters;
}