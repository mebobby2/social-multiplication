kill $(ps aux | grep 'social-multiplication/service-registry' | awk '{print $2}')
kill $(ps aux | grep 'social-multiplication/gateway' | awk '{print $2}')
kill $(ps aux | grep 'social-multiplication/multiplication' | awk '{print $2}')
kill $(ps aux | grep 'social-multiplication/gamification' | awk '{print $2}')
kill $(ps aux | grep 'jetty' | awk '{print $2}')

brew services stop rabbitmq
