package com.project_4.cookpad_api.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;
    private int limit;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String nameProduct;
    private String price;
    private String search;
    private String namePost;
    private String sort;
    private String start;
    private String end;


        public static final class SearchBodyBuilder {
            private int page;
            private int limit;
            private String username;
            private String fullName;
            private String email;
            private String phone;
            private String nameProduct;
            private String search;
            private String price;
            private String namePost;
            private String sort;
            private String start;
            private String end;

            private SearchBodyBuilder() {
            }

            public static SearchBodyBuilder aSearchBody() {
                return new SearchBodyBuilder();
            }

            public SearchBodyBuilder withPage(int page) {
                this.page = page;
                return this;
            }

            public SearchBodyBuilder withUsername(String username) {
                this.username = username;
                return this;
            }
            public SearchBodyBuilder withSearchBody(String search) {
                this.search = search;
                return this;
            }

            public SearchBodyBuilder withFullName(String fullName) {
                this.fullName = fullName;
                return this;
            }

            public SearchBodyBuilder withEmail(String email) {
                this.email = email;
                return this;
            }

            public SearchBodyBuilder withPrice(String price) {
                this.price = price;
                return this;
            }

            public SearchBodyBuilder withNamePost(String namePost) {
                this.namePost = namePost;
                return this;
            }

            public SearchBodyBuilder withLimit(int limit) {
                this.limit = limit;
                return this;
            }

            public SearchBodyBuilder withPhone(String phone) {
                this.phone = phone;
                return this;
            }

            public SearchBodyBuilder withNameProduct(String nameProduct) {
                this.nameProduct = nameProduct;
                return this;
            }

            public SearchBodyBuilder withSort(String sort) {
                this.sort = sort;
                return this;
            }

            public SearchBodyBuilder withStart(String start) {
                this.start = start;
                return this;
            }

            public SearchBodyBuilder withEnd(String end) {
                this.end = end;
                return this;
            }



            public SearchBody build() {
                SearchBody searchBody = new SearchBody();
                searchBody.setPage(page);
                searchBody.setLimit(limit);
                searchBody.setUsername(username);
                searchBody.setEmail(email);
                searchBody.setFullName(fullName);
                searchBody.setNamePost(namePost);
                searchBody.setSearch(search);
                searchBody.setPrice(price);
                searchBody.setPhone(phone);
                searchBody.setNameProduct(nameProduct);
                searchBody.setSort(sort);
                searchBody.setStart(start);
                searchBody.setEnd(end);
                return searchBody;
            }
        }
    }
