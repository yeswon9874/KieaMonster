package org.tain.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.httpClient.MonHttpClient;
import org.tain.node.MonJsonNode;
import org.tain.properties.ProjEnvBaseProperties;
import org.tain.repository.TbResultRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/rest/result"})
@Slf4j
public class ResultRestController {

	@Autowired
	private ProjEnvBaseProperties projEnvBaseProperties;
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	@Autowired
	private TbResultRepository tbResultRepository;
	
	@RequestMapping(value = {"/tbResult"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> list(HttpEntity<String> httpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		System.out.println(">>>>> 1. httpEntity.getHeaders(): " + httpEntity.getHeaders());
		System.out.println(">>>>> 2. httpEntity.getBody(): " + httpEntity.getBody());
		
		if (Flag.flag) {
			// insert the above body to TB_RESULT
		}
		
		if (Flag.flag) {
			// send the signal to the monitor
			this.sendSignal(httpEntity.getBody());
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=" + this.projEnvBaseProperties.getCharSet());
		
		return new ResponseEntity<>("{}", headers, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private MonHttpClient monHttpClient;
	
	private void sendSignal(String httpBody) throws Exception {
		log.info("KANG-20210412 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// httpClient
			///////////////////////////////////////////////
			// 
			MonJsonNode info = new MonJsonNode("{}");
			if (Flag.flag) info.put("url", "http://localhost:8013/v0.1/rest/result/tbResult");
			
			info.put("method", "POST");
			
			MonJsonNode header = new MonJsonNode("{}");
			header.put("Content-Type", "application/json");
			header.put("header01", "value01");
			
			MonJsonNode request = new MonJsonNode(httpBody);
			//request.put("param01", "value01");
			
			MonJsonNode response = new MonJsonNode("{}");
			
			///////////////////////////////////////////////
			//
			MonJsonNode send = new MonJsonNode("{}");
			send.put("_info", info);
			send.put("_header", header);
			send.put("_request", request);
			send.put("_response", response);
			log.info("KANG-20210412 >>>>> send node: " + send.toPrettyString());
			
			///////////////////////////////////////////////
			//
			log.info("KANG-20210412 >>>>> info     node: " + send.getMonJsonNode("_info"    ).toPrettyString());
			log.info("KANG-20210412 >>>>> info     node: " + send.getMap("_info"));
			log.info("KANG-20210412 >>>>> header   node: " + send.getMonJsonNode("_header"  ).toPrettyString());
			log.info("KANG-20210412 >>>>> request  node: " + send.getMonJsonNode("_request" ).toPrettyString());
			log.info("KANG-20210412 >>>>> response node: " + send.getMonJsonNode("_response").toPrettyString());
			
			///////////////////////////////////////////////
			//
			MonJsonNode recv = this.monHttpClient.execute(send);
			log.info("KANG-20210412 >>>>> recv node: " + recv.toPrettyString());
			
			MonJsonNode nodeCmds = recv.getMonJsonNode("_response");
			log.info("KANG-20210412 >>>>> nodeCmds node: " + nodeCmds.toPrettyString());
		}
	}
	
	/*
	@GetMapping(value = {"/list/{userId}"})
	public String listByUserId(@PathVariable(value = "userId") String userId, Pageable pageable, Model model) {
		log.info("KANG-20200803 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("boardList", this.boardService.findBoardListByUserId(pageable, userId));
		return "board/list";
	}
	*/
}
