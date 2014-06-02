package afip.tecno.alfresco.model;

public class TecnoRatingsModel {
	// Namespaces
	public static final String NAMESPACE_TECNO_RATINGS_CONTENT_MODEL = "http://tecno.afip.gob.ar/model/ratings/1.0";

	// Types
	public static final String TYPE_SCR_RATING = "rating";

	// Aspects
	public static final String ASPECT_SCR_RATEABLE = "rateable";

	// Properties
	public static final String PROP_RATING = "rating";
	public static final String PROP_RATER = "rater";
	public static final String PROP_AVERAGE_RATING = "averageRating";
	public static final String PROP_TOTAL_RATING = "totalRating";
	public static final String PROP_RATING_COUNT = "ratingCount";

	// Associations
	public static final String ASSN_SCR_RATINGS = "ratings";
}
