rootProject.name = "kafka-event-driven-architecture"

include("api")
include("common")
include("domain")
include("adapter")
include("adapter:mysql")

include("usecase")
include("usecase:core")
include("usecase:post-resolving-help")
include("usecase:post")
include("usecase:subscribing-post")
include("usecase:inspected-post")
include("usecase:post-search")

include("adapter:metadata-client")
include("adapter:kafka")
include("adapter:chat-gpt-client")
include("adapter:redis")
include("adapter:mongodb")
include("adapter:elasticsearch")

include("worker")
include("worker:auto-inspection")
include("worker:content-subscribing")
include("worker:content-caching")

include("worker:content-indexing")