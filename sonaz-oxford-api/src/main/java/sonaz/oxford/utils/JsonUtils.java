package sonaz.oxford.utils;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtils
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

	public static ObjectMapper getObjectMapperInstance()
	{
		return new ObjectMapper();
	}

	public static <T> List<T> deserializeList(String serializedList, Class<T> objectInListClass)
		throws JsonMappingException,
		JsonProcessingException,
		IOException
	{
		final TypeFactory factory = getObjectMapperInstance().getTypeFactory();
		final JavaType listOfT = factory.constructCollectionType(List.class, objectInListClass);

		return getObjectMapperInstance().readValue(serializedList, listOfT);
	}

	public static <T> List<T> deserializeList(
		String serializedList,
		Class<T> objectInListClass,
		List<T> returnIfException
	)
		throws IOException
	{
		try
		{
			return deserializeList(serializedList, objectInListClass);
		}
		catch (JsonProcessingException e)
		{
			LOGGER.warn("Can't deserialize list : {0}", e.getMessage());
			return returnIfException;
		}
	}

	public static String serializeToString(Object objectToSerialize) throws JsonProcessingException
	{
		return getObjectMapperInstance().writeValueAsString(objectToSerialize);
	}

	public static String serializeToString(Object objectToSerialize, String returnIfException)
	{
		try
		{
			return serializeToString(objectToSerialize);
		}
		catch (JsonProcessingException e)
		{
			LOGGER.warn("Can't serialize object {0}, error : {1}", objectToSerialize, e.getMessage());
			return returnIfException;
		}
	}

}
