package sonaz.oxford.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import sonaz.oxford.model.ImportOxfordResult;
import sonaz.oxford.utils.JsonUtils;

@RestController
@RequestMapping("/oxford")
@SuppressWarnings({
	"rawtypes", "unchecked"
})
public class SonazOxfordApiController
{
	final String oxfordUri = "https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/";

	@RequestMapping(value = "/dictionnaire", method = RequestMethod.GET)
	public Object getOxfordJson(@RequestParam("word") String word)
		throws JsonMappingException,
		JsonProcessingException
	{

		final String uri = oxfordUri + word + "?strictMatch=false";

		/**
		 * curl -X GET --header 'Accept: application/json' --header 'app_id: 87a6b941'
		 * --header 'app_key: e1044da7ee50706f43deeed2d6c1b44c'
		 * 'https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/ace?strictMatch=false'
		 */
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("app_key", "e1044da7ee50706f43deeed2d6c1b44c");
		headers.set("app_id", "87a6b941");
		headers
			.add(
				"user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
			);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
		return response;
	}

	@RequestMapping(value = "/definitions", method = RequestMethod.GET)
	public Object OxfordDefinition(@RequestParam("word") String word)
		throws JsonMappingException,
		JsonProcessingException
	{

		final String uri = oxfordUri + word + "?strictMatch=false";

		/**
		 * curl -X GET --header 'Accept: application/json' --header 'app_id: 87a6b941'
		 * --header 'app_key: e1044da7ee50706f43deeed2d6c1b44c'
		 * 'https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/ace?strictMatch=false'
		 */
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("app_key", "e1044da7ee50706f43deeed2d6c1b44c");
		headers.set("app_id", "87a6b941");
		headers
			.add(
				"user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
			);

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<ImportOxfordResult> response = restTemplate
			.exchange(uri, HttpMethod.GET, entity, ImportOxfordResult.class);

		ImportOxfordResult respones = response.getBody();
		List<ImportOxfordResult> listResponse = Arrays.asList(respones);

		List<ImportOxfordResult> oxfordResults = Arrays
			.asList(JsonUtils.getObjectMapperInstance().readValue(JsonUtils.serializeToString(listResponse), ImportOxfordResult[].class));

		return oxfordResults;

	}

	@RequestMapping(value = "/synonyms", method = RequestMethod.GET)
	public Object searchSynonyms(@RequestParam("word") String word)
	{
		//Object object = OxfordDefinition(word);

		return null;

	}

}
