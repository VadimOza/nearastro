dev:
	docker-compose up -d

test:
	./gradlew clean test

clean:
	docker-compose logs
	docker-compose down --remove-orphans --volumes
