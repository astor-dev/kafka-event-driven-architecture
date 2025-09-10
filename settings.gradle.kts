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
include("adapter:metadata-client")