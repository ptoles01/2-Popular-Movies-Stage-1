package com.ptoles.popularmovies.model;

public class MovieReview {
        private String author;
        private String content;
        private String url;

        public MovieReview(String author, String content, String url) {
            this.author = author;
            this.content = content;
            this.url = url;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    @Override
    public String toString() {
        return "MovieReview{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
