class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'welcome', action:'index')
        "/login/$username/$password"(controller:'login'){
            action = [POST:'index']
        }
        "/register/$username/$displayname/$password/$confirmpassword"(controller:'register',action: 'index')
        "/profile/$viewing?"(controller: 'profile',action: 'index')
        "/get/$type/$target"(controller: 'getter', action:'index')
        "/set/$type/$from/$target/$item"(controller: 'setter', action: 'index')
        "/logout"(controller:'logout',action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
