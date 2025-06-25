@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie getMovieByImdbId(String imdbId) {
        try {
            String url = String.format(
                    "https://www.omdbapi.com/?apikey=%s&i=%s", apiKey, imdbId
            );

            Movie movie = restTemplate.getForObject(url, Movie.class);

            if (movie == null || movie.getTitle() == null || movie.getTitle().equalsIgnoreCase("N/A")) {
                throw new RuntimeException("No se ha encontrado esta película en OMDb");
            }

            return new Movie(movie.getTitle(), movie.getYear(), movie.getPosterUrl());

        } catch (Exception e) {
            throw new RuntimeException("Ha habido un error al obtener la película: " + e.getMessage());
        }
    }
}

