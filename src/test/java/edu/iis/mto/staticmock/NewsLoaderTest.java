package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.reader.NewsReader;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest {

	@Test
	public void publicAndSubscribentNewsSeparation() {
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configLoader = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configLoader);
		when(configLoader.loadConfiguration()).thenReturn(new Configuration());
		
		mockStatic(NewsReaderFactory.class);
		NewsReader newsReader = mock(NewsReader.class);
		when(NewsReaderFactory.getReader(anyString())).thenReturn(newsReader);
		
		IncomingNews incomingNews = createIncomingNews();
		incomingNews.add(createIncomingInfo(SubsciptionType.A));
		incomingNews.add(createIncomingInfo(SubsciptionType.B));
		incomingNews.add(createIncomingInfo(SubsciptionType.C));
		incomingNews.add(createIncomingInfo(SubsciptionType.NONE));
		incomingNews.add(createIncomingInfo(SubsciptionType.NONE));
		incomingNews.add(createIncomingInfo(SubsciptionType.NONE));
		incomingNews.add(createIncomingInfo(SubsciptionType.NONE));
		
		when(newsReader.read()).thenReturn(incomingNews);
		
		NewsLoader newsLoader = new NewsLoader();
		PublishableNews publishableNews = newsLoader.loadNews();
		
		assertThat(publishableNews.getPublicContent().size(), is(4));
		assertThat(publishableNews.getSubscribentContent().size(), is(3));
	}
	
	@Test
	public void checkIfMethodsAreCalledOnce() {
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configLoader = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configLoader);
		when(configLoader.loadConfiguration()).thenReturn(new Configuration());
		
		mockStatic(NewsReaderFactory.class);
		NewsReader newsReader = mock(NewsReader.class);
		when(NewsReaderFactory.getReader(anyString())).thenReturn(newsReader);
		
		IncomingNews incomingNews = createIncomingNews();
		
		when(newsReader.read()).thenReturn(incomingNews);
		
		NewsLoader newsLoader = new NewsLoader();
		newsLoader.loadNews();
		
		verify(configLoader, times(1)).loadConfiguration();
		verify(newsReader, times(1)).read();
	}
	
	
	private IncomingInfo createIncomingInfo(SubsciptionType subscriptionType){
		return new IncomingInfo("test test", subscriptionType);
	}
	
	private IncomingNews createIncomingNews(){
		return new IncomingNews();
	}

}
