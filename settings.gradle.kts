rootProject.name = "kafka-event-driven-architecture"

include("api")
include("common")
include("domain")
include("adapter")
include("adapter:mysql")

include("use-case")
include("use-case:core")
include("use-case:post-resolving-help")
include("use-case:post")
include("use-case:subscribing-post")
include("use-case:inspected-post")
include("use-case:post-search")

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