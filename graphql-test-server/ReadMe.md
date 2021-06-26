
## GraphQL Demo를 위한 로컬 테스트 서버 입니다.

### 환경구성

* nodeJs가 설치가 되어 있어야합니다.
* IntelliJ의 NodeJS 확장 플러그인 설정이 되어 있으면 좋습니다.

### nodemon 글로벌 설치

```
npm install -g nodemon
```


### npm Init

```
npm init
```


### run script 추가
```
"start": "nodemon index.js"
```

### Mock DB를 위해 csv 파일 import
* /database

```
npm i convert-csv-to-json
npm start
```

### Apollo 설치

```
npm i graphql apollo-server
npm i apollo-server-express
```

### 파일 생성 확인

* database.js
```javascript
const csvToJson = require('convert-csv-to-json')

const database = {
  teams: [],
  people: [],
  roles: [],
  softwares: [],
  equipments: [],
  supplies: []
}
Object.keys(database).forEach((key) => {
  database[key] = [
    ...database[key], 
    ...csvToJson.fieldDelimiter(',')
      .getJsonFromCsv(`./data-in-csv/${key}.csv`)
  ]
  if (database[key].length > 0) {
    const firstItem = database[key][0];
    Object.keys(firstItem).forEach((itemKey) => {
      if (database[key].every((item) => {
        return /^-?\d+$/.test(item[itemKey])
      })) {
        database[key].forEach((item) => {
          item[itemKey] = Number(item[itemKey])
        })
      }
    })
  }
})

module.exports = database
```
const database = require('./database')
const { ApolloServer, gql } = require('apollo-server')
const typeDefs = gql`
type Query {
teams: [Team]
}
type Team {
id: Int
manager: String
office: String
extension_number: String
mascot: String
cleaning_duty: String
project: String
}
`
const resolvers = {
Query: {
teams: () => database.teams
}
}

* 모듈화 작업 이후 lodash 모듈 설치
```
npm i lodash
```

### 참고
* 얄코의 GraphQL - 아폴로서버 구축하기.
    * https://www.yalco.kr/@graphql-apollo/2-1/

