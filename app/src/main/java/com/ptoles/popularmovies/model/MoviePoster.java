package com.ptoles.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ptoles.popularmovies.utils.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MoviePoster implements Parcelable{

// https://en.proft.me/2017/02/28/pass-object-between-activities-android-parcelable/
// https://acadgild.com/blog/introduction-serializable-parcelable
// http://www.vogella.com/tutorials/AndroidParcelable/article.html

// We recommend that you use the Bundle class to set primitives known to the OS on Intent objects.
// The Bundle class is highly optimized for marshalling and unmarshalling using parcels.
//
// In some cases, you may need a mechanism to send composite or complex objects across activities.
// In such cases, the custom class should implement Parcelable, and provide the appropriate
// writeToParcel(android.os.Parcel, int) method. It must also provide a non-null field called
// CREATOR that implements the Parcelable.Creator interface, whose createFromParcel() method is
// used for converting the Parcel back to the current object.
// https://developer.android.com/guide/components/activities/parcelables-and-bundles

    private String originalTitle;
    private String posterPath;
    private String overview;
    private Double voteCount;
    private String releaseDate;

    private String id;
    private Boolean video;
    private String voteAverage; //(called vote_average in the api)
    private String title;
    private String popularity;
    private String originalLanguage;
    private List<Integer> genreIDs;
    private String backdropPath;
    private Boolean adult;

    private static final String dateFormatTMDB = "yyyy-MM-dd";


    // All argument constructor
    public MoviePoster(Double voteCount,        String id,                Boolean video,
                       String voteAverage,      String title,             String popularity,
                       String posterPath,       String originalLanguage,  String originalTitle,
                       List<Integer> genreIDs,  String backdropPath,      Boolean adult,
                       String overview,         String releaseDate
    )
    {
    this.voteCount     = voteCount;             this.id               = id;
    this.video         = video;                 this.voteAverage      = voteAverage;
    this.title         = title;                 this.popularity       = popularity;
    this.posterPath    = posterPath;            this.originalLanguage = originalLanguage;
    this.originalTitle = originalTitle;         this.genreIDs         = genreIDs;
    this.backdropPath  = backdropPath;          this.adult            = adult;
    this.overview      = overview;              this.releaseDate      = releaseDate;


    }

    // Getters and Setters


    private MoviePoster(Parcel source) {
        if (source.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = source.readDouble();
        }

        id = source.readString();
        byte tmpVideo = source.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        voteAverage = source.readString();
        title = source.readString();
        popularity = source.readString();
        posterPath = source.readString();
        originalLanguage = source.readString();
        originalTitle = source.readString();

        if (source.readByte() == 0) {
            genreIDs = new ArrayList<>();
            source.readList(genreIDs, Integer.class.getClassLoader());
        } else {
            genreIDs = null;
        }

        backdropPath = source.readString();
        byte tmpAdult = source.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        overview = source.readString();
        releaseDate = source.readString();
    }

    public static final Creator<MoviePoster> CREATOR = new Creator<MoviePoster>() {
        //CREATOR is a static field which calls a constructor and returns a new object
        @Override
        public MoviePoster createFromParcel(Parcel source) {
            return new MoviePoster(source);
        }

        @Override
        public MoviePoster[] newArray(int size) {
            return new MoviePoster[size];
        }
    };

    public Double getVoteCount() {return voteCount;}
    public void setVoteCount(Double voteCount) {this.voteCount = voteCount;}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public Boolean getVideo() {
        return video;
    }
    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }
    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        /* Base URL is from the movie database (TMDB)
         * Size is from a predefined list of values:
         *          "w92", "w154", "w185", "w342", "w500", "w780",
         *          or "original". For most phones “w185” is the recommended width.
         * posterPath is downloaded info from the website image.tmdb.org
         */
        //baseURL + size + posterPath
        final  String BASE_URL = JsonParser.URL_BASE;
        final String SIZE = "w185";
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIDs() {
        return genreIDs;
    }
    public void setGenreIDs(List<Integer> genreIDs) {
        this.genreIDs = genreIDs;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean isAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteCount);
        }
        parcel.writeString(id);
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        parcel.writeString(voteAverage);
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);

        if (genreIDs == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeList(genreIDs);
        }

        parcel.writeString(backdropPath);
        parcel.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }
}
