package com.astordev.ugc.subscribingpost

import org.springframework.data.mongodb.repository.MongoRepository


interface SubscribingPostRepository : SubscribingPostCustomRepository, MongoRepository<SubscribingPostDocument, String>