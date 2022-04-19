import React from 'react';

import GraphiQL from 'graphiql';
import {createGraphiQLFetcher} from '@graphiql/toolkit';

/**
 * A custom React component that extends from the official GraphiQL component. The customizations are tailored to the
 * 'custom-directives' project.
 */
export default function MyGraphiql() {
    const fetcher = createGraphiQLFetcher({
        url: 'http://localhost:8080/graphql',
    });
    return <GraphiQL fetcher={fetcher}/>
}
