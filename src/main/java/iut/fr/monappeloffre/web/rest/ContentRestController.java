

 /*@RestController
@RequestMapping(path="/api/content")
public class ContentRestController {

	private static Logger log = LoggerFactory.getLogger(ContentRestController.class);
	
	@Autowired
	PhotoRepository photoRepository;
	
	

public static class ImageData {
public String fileName;
public byte[] data;
}
	@RequestMapping(
			path="/imagesData",
			//method=RequestMethod.GET, 
			//name="/images"
			//?? consumes="multipart/form-data"
			method=RequestMethod.POST,
			consumes = {"multipart/form-data"})
			
	public void uploadImages(//@RequestBody ImageData image){
			@RequestParam("file") MultipartFile file) {
		//log.info("uploading file");
	    //@RequestPart("file") MultipartFile file) {
		Photo photo = new Photo();
		photo.setDateCreate(LocalDate.now());
		photo.setTitle(file.getName());
		photo.setUri("fee");
		photo.setFormat("fefe");
		photo.setSize((int) file.getSize());
		log.info("uploading file '" + file.getOriginalFilename() + "' ");
		try {
			photo.setImage(file.getBytes());
			photoRepository.save(photo);
		} catch (Exception ex) {
			log.error("Failed to upload", ex);
		}
	
	}
	
	
	
}*/
