#!/bin/bash

# Generate namespace based on build number
namespace="$1"

# generate a yaml file for debug purpose
yaml="$1.yaml"

replacement="s/{.NAMESPACE}/$namespace/;";

# Generate the deployment yaml
cat deployment/kubernetes/hello-world-kube.yaml | sed -e "$replacement" > $yaml

# Make the deployment
kubectl apply -f $yaml