package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest {

	@Test
	public void test() {
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configLoader = mock(ConfigurationLoader.class);
		
		when(ConfigurationLoader.getInstance()).thenReturn(configLoader);
		
	}
	
	private IncomingInfo createIncomingInfo(SubsciptionType subscriptionType){
		return new IncomingInfo("test test", subscriptionType);
	}
	
	private IncomingNews createIncomingNews(){
		return new IncomingNews();
	}

}
