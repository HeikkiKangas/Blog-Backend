# Blog-Backend

## URLs

### Base url
`localhost:8080/api`

### Fetch all posts
`GET /posts`
`curl localhost:8080/api/posts`

### Fetch single post
`GET /posts/{id}`
`curl localhost:8080/api/posts/1`

### Add post
`POST /posts`
`curl -X POST localhost:8080/api/posts -d "{ < BlogPost > }" -H "Content-Type:application/json"`

### Modify post
`PATCH /posts/{id}`
`curl -X PATCH localhost:8080/api/posts/{id} -d "{ < BlogPost > }" -H "Content-Type:application/json"`
`curl -X PATCH http://localhost:8080/api/posts/6 -d "{ \"id\":6, \"author\":{ \"id\":1,\"userName\":\"Admin\",\"admin\":true }, \"timestamp\":\"9999-99-99 99:99\" }" -H Content-Type:application/json`

### Delete post
`DELETE /posts/{id}`
`curl -X DELETE localhost:8080/api/posts/{id}`

## < BlogPost >
`
{
  id: 1,
  title: 'post title',
  text: 'the blog post',
  timestamp: '2020-12-22',
  comments: [
    < Comment1 >,
    < Comment2 >,
    < Comment3 >
  ],
  likes: [
    'username1',
    'username2',
    'username3'
  ]
  tags: [
    'tag1',
    'tag2',
    'tag3'
  ]
}
`

## < Comment >
`
{
  id: 1,
  postID: 15,
  author: 'username',
  text: 'comment text',
  likes: 33,
  timestamp: '2020-12-22'
}
`
