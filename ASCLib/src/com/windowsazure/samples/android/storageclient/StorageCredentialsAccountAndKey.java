package com.windowsazure.samples.android.storageclient;

import java.net.URI;
import java.security.InvalidKeyException;

import org.apache.http.client.methods.HttpRequestBase;

public final class StorageCredentialsAccountAndKey extends StorageCredentials
{

    public StorageCredentialsAccountAndKey(String s, byte abyte0[]) throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public StorageCredentialsAccountAndKey(String s, String s1) throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    protected Boolean canCredentialsComputeHmac() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    protected Boolean canCredentialsSignRequest() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    protected Boolean canCredentialsSignRequestLite() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public String computeHmac256(String s)
        throws NotImplementedException, InvalidKeyException
    {
    	throw new NotImplementedException();
    }

    public String computeHmac512(String s)
        throws NotImplementedException, InvalidKeyException
    {
    	throw new NotImplementedException();
    }

    protected Boolean doCredentialsNeedTransformUri() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public String getAccountName()
    {
        return m_Credentials.getAccountName();
    }

    protected String getBase64EncodedKey() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public Credentials getCredentials() throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public void setCredentials(Credentials credentials) throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    public void signRequest(HttpRequestBase request, long l)
        throws NotImplementedException, InvalidKeyException, StorageException
    {
    	throw new NotImplementedException();
    }

    public void signRequestLite(HttpRequestBase request, long l)
        throws NotImplementedException, InvalidKeyException, StorageException
    {
    	throw new NotImplementedException();
    }

    public String toString(Boolean boolean1)
    {
    	return null;
    }

    public URI transformUri(URI uri) throws NotImplementedException
    {
    	throw new NotImplementedException();
    }

    private Credentials m_Credentials;

	@Override
	public String containerEndpointPostfix() {
		return "";
	}
}