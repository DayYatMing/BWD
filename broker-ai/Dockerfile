FROM python:3.13-slim

RUN apt-get update && apt-get install -y ca-certificates && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

RUN pip install --trusted-host pypi.org \
                --trusted-host files.pythonhosted.org \
                -r requirements.txt

COPY . .

EXPOSE 3100
CMD ["python", "-m", "com.bwd.broker.broker"]
