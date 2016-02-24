class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'welcome',action:'index')
        "/load/$seed/$amount/$depth"(controller: 'welcome',action: 'load')
        "/loading"(controller: 'welcome', action: 'isDoneLoading')
        "/graph"(controller:'graph',action: 'index')
        "/graph/path/$start/$end"(controller: 'graph', action: 'findPath')
        "/reader"(controller: 'reader', action: 'index')
        "/read/$url"(controller: 'reader', action: 'parse')
        "/compare/$first/$second"(controller: 'reader', action: 'distance')
        "/kruskals"(controller: 'graph',action: 'spanning')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
