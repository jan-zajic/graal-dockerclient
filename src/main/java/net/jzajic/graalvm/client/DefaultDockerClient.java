/*-
 * -\-\-
x * docker-client
 * --
 * Copyright (C) 2016 Spotify AB
 * Copyright (c) 2014 Oleg Poleshuk
 * Copyright (c) 2014 CyDesign Ltd
 * Copyright (c) 2016 ThoughtWorks, Inc
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package net.jzajic.graalvm.client;

import static com.google.common.base.MoreObjects.*;
import static com.google.common.base.Optional.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;
import static com.google.common.collect.Maps.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.Collections.*;
import static java.util.concurrent.TimeUnit.*;
import static net.jzajic.graalvm.client.ObjectMapperProvider.*;
import static net.jzajic.graalvm.client.VersionCompare.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.io.CharStreams;
import com.google.common.net.HostAndPort;
import com.google.common.net.HttpHeaders;

import net.jzajic.graalvm.client.auth.ConfigFileRegistryAuthSupplier;
import net.jzajic.graalvm.client.auth.FixedRegistryAuthSupplier;
import net.jzajic.graalvm.client.auth.RegistryAuthSupplier;
import net.jzajic.graalvm.client.exceptions.BadParamException;
import net.jzajic.graalvm.client.exceptions.ConflictException;
import net.jzajic.graalvm.client.exceptions.ContainerNotFoundException;
import net.jzajic.graalvm.client.exceptions.ContainerRenameConflictException;
import net.jzajic.graalvm.client.exceptions.DockerCertificateException;
import net.jzajic.graalvm.client.exceptions.DockerException;
import net.jzajic.graalvm.client.exceptions.DockerInterruptedException;
import net.jzajic.graalvm.client.exceptions.DockerRequestException;
import net.jzajic.graalvm.client.exceptions.DockerTimeoutException;
import net.jzajic.graalvm.client.exceptions.ExecCreateConflictException;
import net.jzajic.graalvm.client.exceptions.ExecNotFoundException;
import net.jzajic.graalvm.client.exceptions.ExecStartConflictException;
import net.jzajic.graalvm.client.exceptions.ImageNotFoundException;
import net.jzajic.graalvm.client.exceptions.NetworkNotFoundException;
import net.jzajic.graalvm.client.exceptions.NodeNotFoundException;
import net.jzajic.graalvm.client.exceptions.NonSwarmNodeException;
import net.jzajic.graalvm.client.exceptions.NotFoundException;
import net.jzajic.graalvm.client.exceptions.PermissionException;
import net.jzajic.graalvm.client.exceptions.ServiceNotFoundException;
import net.jzajic.graalvm.client.exceptions.TaskNotFoundException;
import net.jzajic.graalvm.client.exceptions.UnsupportedApiVersionException;
import net.jzajic.graalvm.client.exceptions.VolumeNotFoundException;
import net.jzajic.graalvm.client.http.JsonEntity;
import net.jzajic.graalvm.client.messages.Container;
import net.jzajic.graalvm.client.messages.ContainerChange;
import net.jzajic.graalvm.client.messages.ContainerConfig;
import net.jzajic.graalvm.client.messages.ContainerCreation;
import net.jzajic.graalvm.client.messages.ContainerExit;
import net.jzajic.graalvm.client.messages.ContainerInfo;
import net.jzajic.graalvm.client.messages.ContainerStats;
import net.jzajic.graalvm.client.messages.ContainerUpdate;
import net.jzajic.graalvm.client.messages.Distribution;
import net.jzajic.graalvm.client.messages.ExecCreation;
import net.jzajic.graalvm.client.messages.ExecState;
import net.jzajic.graalvm.client.messages.HostConfig;
import net.jzajic.graalvm.client.messages.Image;
import net.jzajic.graalvm.client.messages.ImageHistory;
import net.jzajic.graalvm.client.messages.ImageInfo;
import net.jzajic.graalvm.client.messages.ImageSearchResult;
import net.jzajic.graalvm.client.messages.Info;
import net.jzajic.graalvm.client.messages.Network;
import net.jzajic.graalvm.client.messages.NetworkConfig;
import net.jzajic.graalvm.client.messages.NetworkConnection;
import net.jzajic.graalvm.client.messages.NetworkCreation;
import net.jzajic.graalvm.client.messages.ProgressMessage;
import net.jzajic.graalvm.client.messages.RegistryAuth;
import net.jzajic.graalvm.client.messages.RegistryConfigs;
import net.jzajic.graalvm.client.messages.RemovedImage;
import net.jzajic.graalvm.client.messages.ServiceCreateResponse;
import net.jzajic.graalvm.client.messages.TopResults;
import net.jzajic.graalvm.client.messages.Version;
import net.jzajic.graalvm.client.messages.Volume;
import net.jzajic.graalvm.client.messages.VolumeList;
import net.jzajic.graalvm.client.messages.swarm.Config;
import net.jzajic.graalvm.client.messages.swarm.ConfigCreateResponse;
import net.jzajic.graalvm.client.messages.swarm.ConfigSpec;
import net.jzajic.graalvm.client.messages.swarm.Node;
import net.jzajic.graalvm.client.messages.swarm.NodeInfo;
import net.jzajic.graalvm.client.messages.swarm.NodeSpec;
import net.jzajic.graalvm.client.messages.swarm.Secret;
import net.jzajic.graalvm.client.messages.swarm.SecretCreateResponse;
import net.jzajic.graalvm.client.messages.swarm.SecretSpec;
import net.jzajic.graalvm.client.messages.swarm.Service;
import net.jzajic.graalvm.client.messages.swarm.ServiceSpec;
import net.jzajic.graalvm.client.messages.swarm.Swarm;
import net.jzajic.graalvm.client.messages.swarm.SwarmInit;
import net.jzajic.graalvm.client.messages.swarm.SwarmJoin;
import net.jzajic.graalvm.client.messages.swarm.SwarmSpec;
import net.jzajic.graalvm.client.messages.swarm.Task;
import net.jzajic.graalvm.client.messages.swarm.UnlockKey;
import net.jzajic.graalvm.client.npipe.NpipeConnectionSocketFactory;

public class DefaultDockerClient implements DockerClient, Closeable {

	/**
	 * Hack: this {@link ProgressHandler} is meant to capture the image ID (or
	 * image digest in Docker 1.10+) of an image being created. Weirdly enough,
	 * Docker returns the ID or digest of a newly created image in the status of a
	 * progress message.
	 *
	 * <p>
	 * The image ID/digest is required to tag the just loaded image since, also
	 * weirdly enough, the pull operation with the <code>fromSrc</code> parameter
	 * does not support the <code>tag</code> parameter. By retrieving the
	 * ID/digest, the image can be tagged with its image name, given its
	 * ID/digest.
	 */
	private static class CreateProgressHandler implements ProgressHandler {

		// The length of the image hash
		private static final int EXPECTED_CHARACTER_NUM1 = 64;
		// The length of the image digest
		private static final int EXPECTED_CHARACTER_NUM2 = 71;

		private final ProgressHandler delegate;

		private String imageId;

		private CreateProgressHandler(ProgressHandler delegate) {
			this.delegate = delegate;
		}

		private String getImageId() {
			Preconditions.checkState(
					imageId != null,
						"Could not acquire image ID or digest following create");
			return imageId;
		}

		@Override
		public void progress(ProgressMessage message) throws DockerException {
			delegate.progress(message);
			final String status = message.status;
			if (status != null && (status.length() == EXPECTED_CHARACTER_NUM1
					|| status.length() == EXPECTED_CHARACTER_NUM2)) {
				imageId = message.status;
			}
		}

	}

	/**
	 * Hack: this {@link ProgressHandler} is meant to capture the image names of
	 * an image being loaded. Weirdly enough, Docker returns the name of a newly
	 * created image in the stream of a progress message.
	 *
	 */
	private static class LoadProgressHandler implements ProgressHandler {

		// The length of the image hash
		private static final Pattern IMAGE_STREAM_PATTERN = Pattern.compile("Loaded image: (?<image>.+)\n");

		private final ProgressHandler delegate;

		private Set<String> imageNames;

		private LoadProgressHandler(ProgressHandler delegate) {
			this.delegate = delegate;
			this.imageNames = new HashSet<>();
		}

		private Set<String> getImageNames() {
			return ImmutableSet.copyOf(imageNames);
		}

		@Override
		public void progress(ProgressMessage message) throws DockerException {
			delegate.progress(message);
			final String stream = message.stream;
			if (stream != null) {
				Matcher streamMatcher = IMAGE_STREAM_PATTERN.matcher(stream);
				if (streamMatcher.matches()) {
					imageNames.add(streamMatcher.group("image"));
				}

			}
		}

	}

	/**
	 * Hack: this {@link ProgressHandler} is meant to capture the image ID of an
	 * image being built.
	 */
	private static class BuildProgressHandler implements ProgressHandler {

		private final ProgressHandler delegate;

		private String imageId;

		private BuildProgressHandler(ProgressHandler delegate) {
			this.delegate = delegate;
		}

		private String getImageId() {
			Preconditions.checkState(
					imageId != null,
						"Could not acquire image ID or digest following build");
			return imageId;
		}

		@Override
		public void progress(ProgressMessage message) throws DockerException {
			delegate.progress(message);

			final String id = message.buildImageId();
			if (id != null) {
				imageId = id;
			}
		}

	}
	// ==========================================================================

	private static final String UNIX_SCHEME = "unix";
	private static final String NPIPE_SCHEME = "npipe";

	private static final Logger log = LoggerFactory.getLogger(DefaultDockerClient.class);

	static final long NO_TIMEOUT = 0;

	private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = SECONDS.toMillis(5);
	private static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);
	private static final int DEFAULT_CONNECTION_POOL_SIZE = 100;

	private final HttpClientBuilder defaultConfig = HttpClients.custom();

	private static final Pattern CONTAINER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9_.-]+$");

	private static final GenericType<List<Container>> CONTAINER_LIST = new GenericType<List<Container>>() {
	};

	private static final GenericType<List<ContainerChange>> CONTAINER_CHANGE_LIST = new GenericType<List<ContainerChange>>() {
	};

	private static final GenericType<List<Image>> IMAGE_LIST = new GenericType<List<Image>>() {
	};

	private static final GenericType<List<Network>> NETWORK_LIST = new GenericType<List<Network>>() {
	};

	private static final GenericType<List<ImageSearchResult>> IMAGES_SEARCH_RESULT_LIST = new GenericType<List<ImageSearchResult>>() {
	};

	private static final GenericType<List<RemovedImage>> REMOVED_IMAGE_LIST = new GenericType<List<RemovedImage>>() {
	};

	private static final GenericType<List<ImageHistory>> IMAGE_HISTORY_LIST = new GenericType<List<ImageHistory>>() {
	};

	private static final GenericType<List<Service>> SERVICE_LIST = new GenericType<List<Service>>() {
	};

	private static final GenericType<Distribution> DISTRIBUTION = new GenericType<Distribution>() {
	};

	private static final GenericType<List<Task>> TASK_LIST = new GenericType<List<Task>>() {
	};

	private static final GenericType<List<Node>> NODE_LIST = new GenericType<List<Node>>() {
	};

	private static final GenericType<List<Config>> CONFIG_LIST = new GenericType<List<Config>>() {
	};

	private static final GenericType<List<Secret>> SECRET_LIST = new GenericType<List<Secret>>() {
	};

	private final CloseableHttpClient client;
	private final CloseableHttpClient noTimeoutClient;

	private final URI uri;
	private final String apiVersion;
	private final RegistryAuthSupplier registryAuthSupplier;

	private final Map<String, Object> headers;
	private final BasicCredentialsProvider provider = new BasicCredentialsProvider();

	private final Boolean requestEntityChunked;
	
	CloseableHttpClient getClient() {
		return client;
	}

	CloseableHttpClient getNoTimeoutClient() {
		return noTimeoutClient;
	}

	/**
	 * Create a new client with default configuration.
	 *
	 * @param uri
	 *          The docker rest api uri.
	 */
	public DefaultDockerClient(final String uri) {
		this(URI.create(uri.replaceAll("^unix:///", "unix://localhost/")));
	}

	/**
	 * Create a new client with default configuration.
	 *
	 * @param uri
	 *          The docker rest api uri.
	 */
	public DefaultDockerClient(final URI uri) {
		this(new Builder().uri(uri));
	}

	/**
	 * Create a new client with default configuration.
	 *
	 * @param uri
	 *          The docker rest api uri.
	 * @param dockerCertificatesStore
	 *          The certificates to use for HTTPS.
	 */
	public DefaultDockerClient(final URI uri, final DockerCertificatesStore dockerCertificatesStore) {
		this(new Builder().uri(uri).dockerCertificates(dockerCertificatesStore));
	}

	/**
	 * Create a new client using the configuration of the builder.
	 *
	 * @param builder
	 *          DefaultDockerClient builder
	 */
	protected DefaultDockerClient(final Builder builder) {
		final URI originalUri = checkNotNull(builder.uri, "uri");
		checkNotNull(originalUri.getScheme(), "url has null scheme");
		this.apiVersion = builder.apiVersion();

		if ((builder.dockerCertificatesStore != null) && !originalUri.getScheme().equals("https")) {
			throw new IllegalArgumentException(
					"An HTTPS URI for DOCKER_HOST must be provided to use Docker client certificates");
		}

		if (originalUri.getScheme().equals(UNIX_SCHEME)) {
			this.uri = UnixConnectionSocketFactory.sanitizeUri(originalUri);
		} else if (originalUri.getScheme().equals(NPIPE_SCHEME)) {
			this.uri = NpipeConnectionSocketFactory.sanitizeUri(originalUri);
		} else {
			this.uri = originalUri;
		}

		final PoolingHttpClientConnectionManager cm = getConnectionManager(builder);
		final PoolingHttpClientConnectionManager noTimeoutCm = getConnectionManager(builder);

		final RequestConfig.Builder requestConfigBuilder = RequestConfig
				.custom()
					.setConnectionRequestTimeout((int) builder.connectTimeoutMillis)
					.setConnectTimeout((int) builder.connectTimeoutMillis)
					.setSocketTimeout((int) builder.readTimeoutMillis);
					

		final HttpClientBuilder clientBuilder = updateProxy(defaultConfig, builder)
				.setConnectionManager(cm);
		
		if (builder.registryAuthSupplier == null) {
			this.registryAuthSupplier = new FixedRegistryAuthSupplier();
		} else {
			this.registryAuthSupplier = builder.registryAuthSupplier;
		}

		this.requestEntityChunked = builder.requestEntityChunked;

		RequestConfig requestConfig = requestConfigBuilder.build();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		this.client = clientBuilder.build();

		// ApacheConnector doesn't respect per-request timeout settings.
		// Workaround: instead create a client with infinite read timeout,
		// and use it for waitContainer, stopContainer, attachContainer, logs, and build
		final RequestConfig noReadTimeoutRequestConfig = RequestConfig
				.copy(requestConfig)
					.setSocketTimeout((int) NO_TIMEOUT)
					.build();
		this.noTimeoutClient = HttpClientBuilder
				.create()
					.setDefaultRequestConfig(noReadTimeoutRequestConfig)
					.setConnectionManager(noTimeoutCm)
					.build();

		this.headers = new HashMap<>(builder.headers());
	}

	private HttpClientBuilder updateProxy(HttpClientBuilder config, Builder builder) {
		if (builder.useProxy()) {
			final String proxyHost = System.getProperty("http.proxyHost");
			if (proxyHost != null) {
				boolean skipProxy = false;
				final String nonProxyHosts = System.getProperty("http.nonProxyHosts");
				if (nonProxyHosts != null) {
					String host = getHost();
					for (String nonProxyHost : nonProxyHosts.split("\\|")) {
						if (host.matches(toRegExp(nonProxyHost))) {
							skipProxy = true;
							break;
						}
					}
				}

				if (!skipProxy) {
					String proxyPort = checkNotNull(System.getProperty("http.proxyPort"), "http.proxyPort");
					String proxyURI = (!proxyHost.startsWith("http") ? "http://" : "") + proxyHost + ":" + proxyPort;
					HttpHost proxy = HttpHost.create(proxyURI);
					config.setProxy(proxy);

					final String proxyUser = System.getProperty("http.proxyUser");
					if (proxyUser != null) {
						final String proxyPassword = System.getProperty("http.proxyPassword");
						if (proxyPassword != null) {
							config.setProxyAuthenticationStrategy(ProxyAuthenticationStrategy.INSTANCE);
							config.setDefaultCredentialsProvider(provider);
							provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUser, proxyPassword));
						}
					}

					//ensure Content-Length is populated before sending request via proxy.
					//config.property(ClientProperties.REQUEST_ENTITY_PROCESSING,
					//        RequestEntityProcessing.BUFFERED);
				}
			}
		}
		return config;
	}

	private String toRegExp(String hostnameWithWildcards) {
		return hostnameWithWildcards.replace(".", "\\.").replace("*", ".*");
	}

	public String getHost() {
		return fromNullable(uri.getHost()).or("localhost");
	}

	private PoolingHttpClientConnectionManager getConnectionManager(Builder builder) {
		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(getSchemeRegistry(builder));

		// Use all available connections instead of artificially limiting ourselves to 2 per server.
		cm.setMaxTotal(builder.connectionPoolSize);
		cm.setDefaultMaxPerRoute(cm.getMaxTotal());

		return cm;
	}

	private Registry<ConnectionSocketFactory> getSchemeRegistry(final Builder builder) {		
		final RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
				.<ConnectionSocketFactory>create()					
					.register("http", PlainConnectionSocketFactory.getSocketFactory());

		if(builder.uri.getScheme().equals("https")) {
			final SSLConnectionSocketFactory https;
			if (builder.dockerCertificatesStore == null) {
				https = SSLConnectionSocketFactory.getSocketFactory();
			} else {
				https = new SSLConnectionSocketFactory(
						builder.dockerCertificatesStore.sslContext(),
							builder.dockerCertificatesStore.hostnameVerifier());
			}
			registryBuilder.register("https", https);
		}
		
		if (builder.uri.getScheme().equals(UNIX_SCHEME)) {
			registryBuilder.register(UNIX_SCHEME, new UnixConnectionSocketFactory(builder.uri));
		}
		
		if (builder.uri.getScheme().equals(NPIPE_SCHEME)) {
			registryBuilder.register(NPIPE_SCHEME, new NpipeConnectionSocketFactory(builder.uri));
		}
		
		return registryBuilder.build();
	}

	@Override
	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			log.warn("Error closing client", e);
		}
		try {
			noTimeoutClient.close();
		} catch (IOException e) {
			log.warn("Error closing noTimeoutClient", e);
		}
	}

	private void request(final HttpRequestBase req, URIResource resource)
			throws DockerException {
		request(req, (Class) null, resource);
	}

	private void request(final HttpRequestBase req, URIResource resource, ContentType contentType)
			throws DockerException {
		request(req, (Class) null, resource, contentType);
	}

	private <T> T request(final HttpRequestBase req, final GenericType<T> type, URIResource resource)
			throws DockerException {
		return request(req, type, resource, (String) null);
	}

	private <T> T request(final HttpRequestBase req, final GenericType<T> type, URIResource resource, ContentType contentType) {
		return request(req, type, resource, contentType.getMimeType());
	}

	private <T> T request(final HttpRequestBase req, final GenericType<T> type, URIResource resource, String contentType)
			throws DockerException {
		return request(req, type, resource, contentType, null);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource)
			throws DockerException {
		return request(req, clazz, resource, (String) null);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource, String contentType)
			throws DockerException {
		return request(req, clazz, resource, contentType, null);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource, ContentType contentType)
			throws DockerException {
		return request(req, clazz, resource, contentType, null);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource, final HttpEntity entity) {
		return request(req, clazz, resource, (String) null, entity);
	}

	private <T> T request(final HttpRequestBase req, final GenericType<T> type, URIResource resource, ContentType contentType, final HttpEntity entity) {
		return request(req, type, resource, contentType.getMimeType(), entity);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource, ContentType contentType, final HttpEntity entity) {
		return request(req, clazz, resource, contentType.getMimeType(), entity);
	}

	private <T> T request(final HttpRequestBase req, final GenericType<T> type, URIResource resource, String contentType, final HttpEntity entity) {
		return request(req, type(type), resource, contentType, entity);
	}

	private <T> T request(final HttpRequestBase req, final Class<T> clazz, URIResource resource, String contentType, final HttpEntity entity) {
		return request(req, type(clazz), resource, contentType, entity);
	}

	@SuppressWarnings("unchecked")
	private <T> T request(HttpRequestBase req, final JavaType type, URIResource resource, String contentType, final HttpEntity entity) {
		try {
			req.setURI(resource.build());
			if (contentType != null) {
				req.addHeader(HttpHeaders.ACCEPT, contentType);
			}
			req = headers(req);
			if (req instanceof HttpEntityEnclosingRequestBase && entity != null) {
				((HttpEntityEnclosingRequestBase) req).setEntity(entity);
				if(this.requestEntityChunked != null && entity instanceof AbstractHttpEntity) {
					((AbstractHttpEntity) entity).setChunked(this.requestEntityChunked);
				}
			}
			
			if(type != null && CloseableHttpResponse.class.isAssignableFrom(type.getRawClass())) {
				return (T) client.execute(req);
			} else if (type == null) {
				return (T) client.execute(req, new BasicResponseHandler());
			} else if (HttpResponse.class.isAssignableFrom(type.getRawClass())) {
				return (T) client.execute(req, r -> r);
			} else if (type != null && InputStream.class.isAssignableFrom(type.getRawClass())) {
				return (T) client.execute(req, EntityHandler.responseHandler(HttpEntity::getContent));
			} else if (type != null && ProgressStream.class.isAssignableFrom(type.getRawClass())) {
				return (T) client.execute(req, EntityHandler.responseHandler(ProgressStream::ofEntity));
			} else if (type != null && LogStream.class.isAssignableFrom(type.getRawClass())) {
				CloseableHttpResponse response = client.execute(req);
				return (T) EntityHandler.responseHandler(DefaultLogStream::ofEntity).handleResponse(response);
			} else if (type != null && CharSequence.class.isAssignableFrom(type.getRawClass())) {
				return (T) client.execute(req, new BasicResponseHandler());			
			} else {
				return client.execute(req, EntityHandler.objectResponseHandler(type));
			}
		} catch (RuntimeException | URISyntaxException | IOException e) {
			throw propagate(req, e);
		}
	}

	private <T> JavaType type(Class<T> clazz) {
		if(clazz == null)
			return null;
		return ObjectMapperProvider
				.objectMapper()
					.getTypeFactory()
					.constructType(clazz);
	}

	private <T> JavaType type(GenericType<T> type) {
		if(type == null)
			return null;
		return ObjectMapperProvider
				.objectMapper()
					.getTypeFactory()
					.constructType(type.getType());
	}

	@Override
	public String ping() throws DockerException {
		URIResource requestURI = new URIResource(uri).addPath("_ping");
		return request(new HttpGet(), String.class, requestURI);
	}

	@Override
	public Version version() throws DockerException {
		URIResource requestURI = new URIResource(uri).addPath("version");
		return request(new HttpGet(), Version.class, requestURI, ContentType.APPLICATION_JSON);
	}

	@Override
	public int auth(final RegistryAuth registryAuth) throws DockerException {
		final URIResource resource = resource().addPath("auth");
		final HttpResponse response = request(
				new HttpPost(),
					HttpResponse.class,
					resource,
					ContentType.APPLICATION_JSON,
					JsonEntity.create(registryAuth));
		return response.getStatusLine().getStatusCode();
	}

	@Override
	public Info info() throws DockerException {
		final URIResource resource = resource().addPath("info");
		return request(new HttpGet(), Info.class, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Container> listContainers(final ListContainersParam... params)
			throws DockerException {
		URIResource resource = resource()
				.addPath("containers")
					.addPath("json");
		resource = addParameters(resource, params);

		try {
			return request(new HttpGet(), CONTAINER_LIST, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource), e);
			default:
				throw e;
			}
		}
	}

	private URIResource addParameters(URIResource resource, final Param... params)
			throws DockerException {
		final Map<String, List<String>> filters = newHashMap();
		for (final Param param : params) {
			if (param instanceof FilterParam) {
				List<String> filterValueList;
				if (filters.containsKey(param.name())) {
					filterValueList = filters.get(param.name());
				} else {
					filterValueList = Lists.newArrayList();
				}
				filterValueList.add(param.value());
				filters.put(param.name(), filterValueList);
			} else {
				resource.addParameter(param.name(), param.value());
			}
		}

		if (!filters.isEmpty()) {
			// If filters were specified, we must put them in a JSON object and pass them using the
			// 'filters' query param like this: filters={"dangling":["true"]}. If filters is an empty map,
			// urlEncodeFilters will return null and addParameter() will remove that query parameter.
			resource.addParameter("filters", urlEncodeFilters(filters));
		}
		return resource;
	}

	private Map<String, String> getQueryParamMap(final URIResource resource) {
		final Map<String, String> paramsMap = Maps.newHashMap();
		if (resource.getQueryParams() != null) {
			for (final NameValuePair addParameter : resource.getQueryParams()) {
				paramsMap.put(addParameter.getName(), addParameter.getValue());
			}
		}
		return paramsMap;
	}

	/**
	 * Takes a map of filters and URL-encodes them. If the map is empty or an
	 * exception occurs, return null.
	 *
	 * @param filters
	 *          A map of filters.
	 * @return String
	 * @throws DockerException
	 *           if there's an IOException
	 */
	private String urlEncodeFilters(final Map<String, List<String>> filters) throws DockerException {
		try {
			final String unencodedFilters = objectMapper().writeValueAsString(filters);
			if (!unencodedFilters.isEmpty()) {
				return unencodedFilters;
			}
		} catch (IOException e) {
			throw new DockerException(e);
		}
		return null;
	}

	@Override
	public List<Image> listImages(final ListImagesParam... params)
			throws DockerException {
		URIResource resource = resource()
				.addPath("images")
					.addPath("json");
		resource = addParameters(resource, params);
		return request(new HttpGet(), IMAGE_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public ContainerCreation createContainer(final ContainerConfig config)
			throws DockerException {
		return createContainer(config, null);
	}

	@Override
	public ContainerCreation createContainer(final ContainerConfig config, final String name)
			throws DockerException {
		URIResource resource = resource()
				.addPath("containers")
					.addPath("create");

		if (name != null) {
			checkArgument(
					CONTAINER_NAME_PATTERN.matcher(name).matches(),
						"Invalid container name: \"%s\"",
						name);
			resource.addParameter("name", name);
		}

		log.debug("Creating container with ContainerConfig: {}", config);

		try {
			HttpPost post = new HttpPost();
			post.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			return request(post, ContainerCreation.class, resource, ContentType.APPLICATION_JSON, JsonEntity.create(config));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(config.image, e);
			case 406:
				throw new DockerException("Impossible to attach. Container not running.", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void startContainer(final String containerId)
			throws DockerException {
		checkNotNull(containerId, "containerId");

		log.info("Starting container with Id: {}", containerId);

		containerAction(containerId, "start");
	}

	private void containerAction(final String containerId, final String action)
			throws DockerException {
		containerAction(containerId, action, ArrayListMultimap.create());
	}

	private void containerAction(final String containerId, final String action,
			final Multimap<String, String> addParametereters)
			throws DockerException {
		try {
			URIResource resource = resource()
					.addPath("containers")
						.addPath(containerId)
						.addPath(action);

			for (String addParametereter : addParametereters.keySet()) {
				Collection<String> values = addParametereters.get(addParametereter);
				for (String parameterValue : values) {
					resource.addParameter(addParametereter, parameterValue);
				}
			}
			request(new HttpPost(), resource);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void pauseContainer(final String containerId)
			throws DockerException {
		checkNotNull(containerId, "containerId");
		containerAction(containerId, "pause");
	}

	@Override
	public void unpauseContainer(final String containerId)
			throws DockerException {
		checkNotNull(containerId, "containerId");
		containerAction(containerId, "unpause");
	}

	@Override
	public void restartContainer(String containerId) throws DockerException {
		restartContainer(containerId, 10);
	}

	@Override
	public void restartContainer(String containerId, int secondsToWaitBeforeRestart)
			throws DockerException {
		checkNotNull(containerId, "containerId");
		checkNotNull(secondsToWaitBeforeRestart, "secondsToWait");

		Multimap<String, String> addParametereters = ArrayListMultimap.create();
		addParametereters.put("t", String.valueOf(secondsToWaitBeforeRestart));

		containerAction(containerId, "restart", addParametereters);
	}

	@Override
	public void killContainer(final String containerId) throws DockerException {
		checkNotNull(containerId, "containerId");
		containerAction(containerId, "kill");
	}

	@Override
	public void killContainer(final String containerId, final Signal signal)
			throws DockerException {
		checkNotNull(containerId, "containerId");

		Multimap<String, String> addParametereters = ArrayListMultimap.create();
		addParametereters.put("signal", signal.getName());

		containerAction(containerId, "kill", addParametereters);
	}

	@Override
	public Distribution getDistribution(String imageName)
			throws DockerException {
		checkNotNull(imageName, "containerName");
		final URIResource resource = resource().addPath("distribution").addPath(imageName).addPath("json");
		return request(new HttpGet(), DISTRIBUTION, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public void stopContainer(final String containerId, final int secondsToWaitBeforeKilling)
			throws DockerException {
		try {
			final URIResource resource = noTimeoutResource();
			resource.addPath("containers").addPath(containerId).addPath("stop");
			resource.addParameter("t", String.valueOf(secondsToWaitBeforeKilling));
			request(new HttpPost(), resource);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 304: // already stopped, so we're cool
				return;
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ContainerExit waitContainer(final String containerId)
			throws DockerException {
		try {
			final URIResource resource = noTimeoutResource()
					.addPath("containers")
						.addPath(containerId)
						.addPath("wait");
			// Wait forever
			return request(
					new HttpPost(),
						ContainerExit.class,
						resource,
						ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void removeContainer(final String containerId)
			throws DockerException {
		removeContainer(containerId, new RemoveContainerParam[0]);
	}

	@Deprecated
	@Override
	public void removeContainer(final String containerId, final boolean removeVolumes)
			throws DockerException {
		removeContainer(containerId, RemoveContainerParam.removeVolumes(removeVolumes));
	}

	@Override
	public void removeContainer(final String containerId, final RemoveContainerParam... params)
			throws DockerException {
		try {
			URIResource resource = resource().addPath("containers").addPath(containerId);

			for (final RemoveContainerParam param : params) {
				resource.addParameter(param.name(), param.value());
			}

			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource()), e);
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public InputStream exportContainer(String containerId)
			throws DockerException {
		final URIResource resource = resource()
				.addPath("containers")
					.addPath(containerId)
					.addPath("export");
		try {
			return request(new HttpGet(), InputStream.class, resource, ContentType.APPLICATION_OCTET_STREAM);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	@Deprecated
	public InputStream copyContainer(String containerId, String path)
			throws DockerException {
		final String apiVersion = version().apiVersion;
		final int versionComparison = compareVersion(apiVersion, "1.24");

		// Version above 1.24
		if (versionComparison >= 0) {
			throw new UnsupportedApiVersionException(apiVersion);
		}

		final URIResource resource = resource()
				.addPath("containers")
					.addPath(containerId)
					.addPath("copy");

		// Internal JSON object; not worth it to create class for this
		final JsonNodeFactory nf = JsonNodeFactory.instance;
		final JsonNode params = nf.objectNode().set("Resource", nf.textNode(path));

		try {
			return request(
					new HttpPost(),
						InputStream.class,
						resource,
						ContentType.APPLICATION_OCTET_STREAM,
						JsonEntity.create(params));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public InputStream archiveContainer(String containerId, String path)
			throws DockerException {
		final String apiVersion = version().apiVersion;
		final int versionComparison = compareVersion(apiVersion, "1.20");

		// Version below 1.20
		if (versionComparison < 0) {
			throw new UnsupportedApiVersionException(apiVersion);
		}

		final URIResource resource = resource();
		resource.addPath("containers/" + containerId + "/archive");
		resource.addParameter("path", path);

		try {
			return request(
					new HttpGet(),
						InputStream.class,
						resource,
						ContentType.APPLICATION_OCTET_STREAM);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public TopResults topContainer(final String containerId)
			throws DockerException {
		return topContainer(containerId, null);
	}

	@Override
	public TopResults topContainer(final String containerId, final String psArgs)
			throws DockerException {
		try {
			URIResource resource = resource().addPath("containers").addPath(containerId).addPath("top");
			if (!Strings.isNullOrEmpty(psArgs)) {
				resource.addParameter("ps_args", psArgs);
			}
			return request(new HttpGet(), TopResults.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void copyToContainer(final Path directory, String containerId, String path)
			throws DockerException, IOException {
		try (final CompressedDirectory compressedDirectory = CompressedDirectory.create(directory);
				final InputStream fileStream = Files.newInputStream(compressedDirectory.file())) {
			copyToContainer(fileStream, containerId, path);
		}
	}

	@Override
	public void copyToContainer(InputStream tarStream, String containerId, String path)
			throws DockerException {
		final URIResource resource = resource();
		resource.addPath("containers/" + containerId + "/archive");
		resource.addParameter("noOverwriteDirNonDir", "true");
		resource.addParameter("path", path);

		try {
			request(
					new HttpPut(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						new InputStreamEntity(tarStream, ContentType.create("application/tar")));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource), e);
			case 403:
				throw new PermissionException("Volume or container rootfs is marked as read-only.", e);
			case 404:
				throw new NotFoundException(
						String.format("Either container %s or path %s not found.", containerId, path),
							e);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<ContainerChange> inspectContainerChanges(final String containerId)
			throws DockerException {
		try {
			final URIResource resource = resource().addPath("containers").addPath(containerId).addPath("changes");
			return request(
					new HttpGet(),
						CONTAINER_CHANGE_LIST,
						resource,
						ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ContainerInfo inspectContainer(final String containerId)
			throws DockerException {
		try {
			final URIResource resource = resource().addPath("containers").addPath(containerId).addPath("json");
			return request(new HttpGet(), ContainerInfo.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ContainerCreation commitContainer(final String containerId,
			final String repo,
			final String tag,
			final ContainerConfig config,
			final String comment,
			final String author)
			throws DockerException {

		checkNotNull(containerId, "containerId");
		checkNotNull(repo, "repo");
		checkNotNull(config, "containerConfig");

		URIResource resource = resource();
		resource.addPath("commit");
		resource.addParameter("container", containerId);
		resource.addParameter("repo", repo);

		if (!isNullOrEmpty(author)) {
			resource = resource.addParameter("author", author);
		}
		if (!isNullOrEmpty(comment)) {
			resource = resource.addParameter("comment", comment);
		}
		if (!isNullOrEmpty(tag)) {
			resource = resource.addParameter("tag", tag);
		}

		log.debug(
				"Committing container id: {} to repository: {} with ContainerConfig: {}",
					containerId,
					repo,
					config);

		try {
			return request(new HttpPost(), ContainerCreation.class, resource, ContentType.APPLICATION_JSON, JsonEntity.create(config));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void renameContainer(final String containerId, final String name)
			throws DockerException {
		URIResource resource = resource()
				.addPath("containers")
					.addPath(containerId)
					.addPath("rename");

		if (name == null) {
			throw new IllegalArgumentException("Cannot rename container to null");
		}

		checkArgument(
				CONTAINER_NAME_PATTERN.matcher(name).matches(),
					"Invalid container name: \"%s\"",
					name);
		resource = resource.addParameter("name", name);

		log.info("Renaming container with id {}. New name {}.", containerId, name);

		try {
			request(new HttpPost(), resource);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			case 409:
				throw new ContainerRenameConflictException(containerId, name, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ContainerUpdate updateContainer(final String containerId, final HostConfig config)
			throws DockerException {
		assertApiVersionIsAbove("1.22");
		try {
			URIResource resource = resource().addPath("containers").addPath(containerId).addPath("update");
			return request(
					new HttpPost(),
						ContainerUpdate.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(config));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<ImageSearchResult> searchImages(final String term)
			throws DockerException {
		final URIResource resource = resource().addPath("images").addPath("search").addParameter("term", term);
		return request(
				new HttpGet(),
					IMAGES_SEARCH_RESULT_LIST,
					resource,
					ContentType.APPLICATION_JSON);
	}

	@Override
	@Deprecated
	public void load(final String image, final InputStream imagePayload)
			throws DockerException {
		create(image, imagePayload);
	}

	@Override
	@Deprecated
	public void load(final String image, final InputStream imagePayload,
			final ProgressHandler handler)
			throws DockerException {
		create(image, imagePayload, handler);
	}

	@Override
	public Set<String> load(final InputStream imagePayload)
			throws DockerException {
		return load(imagePayload, new LoggingLoadHandler());
	}

	@Override
	public Set<String> load(final InputStream imagePayload, final ProgressHandler handler)
			throws DockerException {
		final URIResource resource = resource()
				.addPath("images")
					.addPath("load")
					.addParameter("quiet", "false");

		final LoadProgressHandler loadProgressHandler = new LoadProgressHandler(handler);
		final HttpEntity entity = new InputStreamEntity(imagePayload, ContentType.APPLICATION_OCTET_STREAM);
		HttpPost post = new HttpPost();
		try (final ProgressStream load = request(
				post,
					ProgressStream.class,
					resource,
					ContentType.APPLICATION_JSON,
					entity)) {
			load.tail(loadProgressHandler, post.getMethod(), post.getURI());
			return loadProgressHandler.getImageNames();
		} catch (IOException e) {
			throw new DockerException(e);
		} finally {
			closeQuietly(imagePayload);
		}
	}

	@Override
	public void create(final String image, final InputStream imagePayload)
			throws DockerException {
		create(image, imagePayload, new LoggingPullHandler("image stream"));
	}

	@Override
	public void create(final String image, final InputStream imagePayload,
			final ProgressHandler handler)
			throws DockerException {
		URIResource resource = resource().addPath("images").addPath("create");

		resource = resource
				.addParameter("fromSrc", "-")
					.addParameter("tag", image);

		final CreateProgressHandler createProgressHandler = new CreateProgressHandler(handler);
		final HttpEntity entity = new InputStreamEntity(imagePayload, ContentType.APPLICATION_OCTET_STREAM);
		HttpPost post = new HttpPost();

		try {
			requestAndTail(
					post,
						createProgressHandler,
						resource,
						ContentType.APPLICATION_JSON,
						entity);
			tag(createProgressHandler.getImageId(), image, true);
		} finally {
			closeQuietly(imagePayload);
		}
	}

	@Override
	public InputStream save(final String... images)
			throws DockerException, IOException {
		URIResource resource;
		if (images.length == 1) {
			resource = resource().addPath("images").addPath(images[0]).addPath("get");
		} else {
			resource = resource().addPath("images").addPath("get");
			if (images.length > 1) {
				for (final String image : images) {
					if (!isNullOrEmpty(image)) {
						resource = resource.addParameter("names", image);
					}
				}
			}
		}

		return request(
				new HttpGet(),
					InputStream.class,
					resource,
					ContentType.APPLICATION_JSON);
	}

	@Override
	public InputStream saveMultiple(final String... images)
			throws DockerException, IOException {

		final URIResource resource = resource().addPath("images").addPath("get");
		for (final String image : images) {
			resource.addParameter("names", image);
		}

		HttpGet get = new HttpGet();
		get.addHeader(
				"X-Registry-Auth",
					authHeader(
							registryAuthSupplier.authFor(images[0])));
		return request(
				get,
					InputStream.class,
					resource,
					ContentType.APPLICATION_JSON);
	}

	@Override
	public void pull(final String image) throws DockerException {
		pull(image, new LoggingPullHandler(image));
	}

	@Override
	public void pull(final String image, final ProgressHandler handler)
			throws DockerException {
		pull(image, registryAuthSupplier.authFor(image), handler);
	}

	@Override
	public void pull(final String image, final RegistryAuth registryAuth)
			throws DockerException {
		pull(image, registryAuth, new LoggingPullHandler(image));
	}

	@Override
	public void pull(final String image, final RegistryAuth registryAuth,
			final ProgressHandler handler)
			throws DockerException {
		final ImageRef imageRef = new ImageRef(image);

		URIResource resource = resource().addPath("images").addPath("create");
		resource.addParameter("fromImage", imageRef.getImage());
		if (imageRef.getTag() != null) {
			resource.addParameter("tag", imageRef.getTag());
		}
		HttpPost post = new HttpPost();
		post.addHeader("X-Registry-Auth", authHeader(registryAuth));
		try {
			requestAndTail(post, handler, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(image, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void push(final String image) throws DockerException {
		push(image, new LoggingPushHandler(image));
	}

	@Override
	public void push(final String image, final RegistryAuth registryAuth)
			throws DockerException {
		push(image, new LoggingPushHandler(image), registryAuth);
	}

	@Override
	public void push(final String image, final ProgressHandler handler)
			throws DockerException {
		push(image, handler, registryAuthSupplier.authFor(image));
	}

	@Override
	public void push(final String image,
			final ProgressHandler handler,
			final RegistryAuth registryAuth)
			throws DockerException {
		final ImageRef imageRef = new ImageRef(image);

		URIResource resource = resource().addPath("images").addPath(imageRef.getImage()).addPath("push");

		if (imageRef.getTag() != null) {
			resource = resource.addParameter("tag", imageRef.getTag());
		}

		HttpPost post = new HttpPost();
		post.addHeader("X-Registry-Auth", authHeader(registryAuth));
		try {
			requestAndTail(
					post,
						handler,
						resource,
						ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(image, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void tag(final String image, final String name)
			throws DockerException {
		tag(image, name, false);
	}

	@Override
	public void tag(final String image, final String name, final boolean force)
			throws DockerException {
		final ImageRef imageRef = new ImageRef(name);

		URIResource resource = resource().addPath("images").addPath(image).addPath("tag");

		resource = resource.addParameter("repo", imageRef.getImage());
		if (imageRef.getTag() != null) {
			resource = resource.addParameter("tag", imageRef.getTag());
		}

		if (force) {
			resource.addParameter("force", "true");
		}

		try {
			request(new HttpPost(), resource);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource), e);
			case 404:
				throw new ImageNotFoundException(image, e);
			case 409:
				throw new ConflictException(e);
			default:
				throw e;
			}
		}
	}

	@Override
	public String build(final Path directory, final BuildParam... params)
			throws DockerException, IOException {
		return build(directory, null, new LoggingBuildHandler(), params);
	}

	@Override
	public String build(final Path directory, final String name, final BuildParam... params)
			throws DockerException, IOException {
		return build(directory, name, new LoggingBuildHandler(), params);
	}

	@Override
	public String build(final Path directory, final ProgressHandler handler,
			final BuildParam... params)
			throws DockerException, IOException {
		return build(directory, null, handler, params);
	}

	@Override
	public String build(final Path directory, final String name, final ProgressHandler handler,
			final BuildParam... params)
			throws DockerException, IOException {
		return build(directory, name, null, handler, params);
	}

	@Override
	public String build(final Path directory, final String name, final String dockerfile,
			final ProgressHandler handler, final BuildParam... params)
			throws DockerException, IOException {
		checkNotNull(handler, "handler");

		URIResource resource = noTimeoutResource().addPath("build");

		for (final BuildParam param : params) {
			resource = resource.addParameter(param.name(), param.value());
		}
		if (name != null) {
			resource = resource.addParameter("t", name);
		}
		if (dockerfile != null) {
			resource = resource.addParameter("dockerfile", dockerfile);
		}

		// Convert auth to X-Registry-Config format
		final RegistryConfigs registryConfigs = registryAuthSupplier.authForBuild();

		final BuildProgressHandler buildHandler = new BuildProgressHandler(handler);

		try (final CompressedDirectory compressedDirectory = CompressedDirectory.create(directory);
				final InputStream fileStream = Files.newInputStream(compressedDirectory.file())) {
			HttpPost post = new HttpPost();
			post.addHeader("X-Registry-Config", authRegistryHeader(registryConfigs));
			requestAndTail(
					post,
						buildHandler,
						resource,
						ContentType.APPLICATION_JSON,
						new InputStreamEntity(fileStream, ContentType.create("application/tar")));

			return buildHandler.getImageId();
		}
	}

	@Override
	public ImageInfo inspectImage(final String image) throws DockerException {
		try {
			final URIResource resource = resource().addPath("images").addPath(image).addPath("json");
			return request(new HttpGet(), ImageInfo.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(image, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<RemovedImage> removeImage(String image)
			throws DockerException {
		return removeImage(image, false, false);
	}

	@Override
	public List<RemovedImage> removeImage(String image, boolean force, boolean noPrune)
			throws DockerException {
		try {
			final URIResource resource = resource()
					.addPath("images")
						.addPath(image)
						.addParameter("force", String.valueOf(force))
						.addParameter("noprune", String.valueOf(noPrune));
			return request(new HttpDelete(), REMOVED_IMAGE_LIST, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(image, e);
			case 409:
				throw new ConflictException(e);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<ImageHistory> history(final String image)
			throws DockerException {
		final URIResource resource = resource()
				.addPath("images")
					.addPath(image)
					.addPath("history");
		try {
			return request(new HttpGet(), IMAGE_HISTORY_LIST, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ImageNotFoundException(image, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public LogStream logs(final String containerId, final LogsParam... params)
			throws DockerException {
		URIResource resource = noTimeoutResource()
				.addPath("containers")
					.addPath(containerId)
					.addPath("logs");

		for (final LogsParam param : params) {
			resource = resource.addParameter(param.name(), param.value());
		}

		return getLogStream(new HttpGet(), resource, containerId);
	}

	@Override
	public EventStream events(EventsParam... params)
			throws DockerException {
		URIResource resource = noTimeoutResource().addPath("events");
		resource = addParameters(resource, params);

		try {
			final CloseableHttpResponse response = noTimeoutClient.execute(new HttpGet(resource.build()));
			return new EventStream(response, objectMapper().getFactory());
		} catch (IOException | URISyntaxException exception) {
			throw new DockerException(exception);
		}
	}

	@Override
	public LogStream attachContainer(final String containerId,
			final AttachParameter... params) throws DockerException {
		checkNotNull(containerId, "containerId");
		URIResource resource = noTimeoutResource().addPath("containers").addPath(containerId).addPath("attach");

		for (final AttachParameter param : params) {
			resource = resource.addParameter(param.name().toLowerCase(Locale.ROOT), String.valueOf(true));
		}

		return getLogStream(new HttpPost(), resource, containerId);
	}

	private LogStream getLogStream(final HttpRequestBase method, final URIResource resource,
			final String containerId)
			throws DockerException {
		try {
			return request(method, LogStream.class, resource, ContentType.create("application/vnd.docker.raw-stream"));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource), e);
			case 404:
				throw new ContainerNotFoundException(containerId);
			default:
				throw e;
			}
		}
	}

	private LogStream getServiceLogStream(final HttpRequestBase method, final URIResource resource,
			final String serviceId)
			throws DockerException {
		try {
			return request(method, LogStream.class, resource, ContentType.create("application/vnd.docker.raw-stream"));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new BadParamException(getQueryParamMap(resource), e);
			case 404:
				throw new ServiceNotFoundException(serviceId);
			default:
				throw e;
			}
		}
	}

	@Override
	public ExecCreation execCreate(final String containerId,
			final String[] cmd,
			final ExecCreateParam... params)
			throws DockerException {
		final ContainerInfo containerInfo = inspectContainer(containerId);
		if (!containerInfo.state.running) {
			throw new IllegalStateException("Container " + containerId + " is not running.");
		}

		final URIResource resource = resource().addPath("containers").addPath(containerId).addPath("exec");

		final StringWriter writer = new StringWriter();
		try {
			final JsonGenerator generator = objectMapper().getFactory().createGenerator(writer);
			generator.writeStartObject();

			for (final ExecCreateParam param : params) {
				if (param.value().equals("true") || param.value().equals("false")) {
					generator.writeBooleanField(param.name(), Boolean.valueOf(param.value()));
				} else {
					generator.writeStringField(param.name(), param.value());
				}
			}

			generator.writeArrayFieldStart("Cmd");
			for (final String s : cmd) {
				generator.writeString(s);
			}
			generator.writeEndArray();

			generator.writeEndObject();
			generator.close();
		} catch (IOException e) {
			throw new DockerException(e);
		}

		try {
			return request(
					new HttpPost(),
						ExecCreation.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(writer.toString()));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			case 409:
				throw new ExecCreateConflictException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public LogStream execStart(final String execId, final ExecStartParameter... params)
			throws DockerException {
		final URIResource resource = noTimeoutResource().addPath("exec").addPath(execId).addPath("start");

		final StringWriter writer = new StringWriter();
		try {
			final JsonGenerator generator = objectMapper().getFactory().createGenerator(writer);
			generator.writeStartObject();

			for (final ExecStartParameter param : params) {
				generator.writeBooleanField(param.getName(), true);
			}

			generator.writeEndObject();
			generator.close();
		} catch (IOException e) {
			throw new DockerException(e);
		}

		try {
			return request(
					new HttpPost(),
						LogStream.class,
						resource,
						ContentType.create("application/vnd.docker.raw-stream"),
						JsonEntity.create(writer.toString()));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ExecNotFoundException(execId, e);
			case 409:
				throw new ExecStartConflictException(execId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public Swarm inspectSwarm() throws DockerException {
		assertApiVersionIsAbove("1.24");

		final URIResource resource = resource().addPath("swarm");
		return request(new HttpGet(), Swarm.class, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public String initSwarm(final SwarmInit swarmInit) throws DockerException {
		assertApiVersionIsAbove("1.24");

		try {
			final URIResource resource = resource().addPath("swarm").addPath("init");
			return request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(swarmInit));

		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new DockerException("bad parameter", e);
			case 500:
				throw new DockerException("server error", e);
			case 503:
				throw new DockerException("node is already part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void joinSwarm(final SwarmJoin swarmJoin) throws DockerException {
		assertApiVersionIsAbove("1.24");

		try {
			final URIResource resource = resource().addPath("swarm").addPath("join");
			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(swarmJoin));

		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new DockerException("bad parameter", e);
			case 500:
				throw new DockerException("server error", e);
			case 503:
				throw new DockerException("node is already part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void leaveSwarm() throws DockerException {
		leaveSwarm(false);
	}

	@Override
	public void leaveSwarm(final boolean force) throws DockerException {
		assertApiVersionIsAbove("1.24");

		try {
			final URIResource resource = resource().addPath("swarm/leave");
			resource.addParameter("force", String.valueOf(force));
			request(new HttpPost(), String.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 500:
				throw new DockerException("server error", e);
			case 503:
				throw new DockerException("node is not part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void updateSwarm(final Long version,
			final boolean rotateWorkerToken,
			final boolean rotateManagerToken,
			final boolean rotateManagerUnlockKey,
			final SwarmSpec spec)
			throws DockerException {
		assertApiVersionIsAbove("1.24");

		try {
			final URIResource resource = resource()
					.addPath("swarm")
						.addPath("update");
			
			resource.addParameter("version", String.valueOf(version));
			resource.addParameter("rotateWorkerToken", String.valueOf(rotateWorkerToken));
			resource.addParameter("rotateManagerToken", String.valueOf(rotateManagerToken));
			resource.addParameter("rotateManagerUnlockKey", String.valueOf(rotateManagerUnlockKey));

			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(spec));

		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 400:
				throw new DockerException("bad parameter", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void updateSwarm(final Long version,
			final boolean rotateWorkerToken,
			final boolean rotateManagerToken,
			final SwarmSpec spec)
			throws DockerException {
		updateSwarm(version, rotateWorkerToken, rotateWorkerToken, false, spec);
	}

	@Override
	public void updateSwarm(final Long version,
			final boolean rotateWorkerToken,
			final SwarmSpec spec)
			throws DockerException {
		updateSwarm(version, rotateWorkerToken, false, false, spec);
	}

	@Override
	public void updateSwarm(final Long version,
			final SwarmSpec spec)
			throws DockerException {
		updateSwarm(version, false, false, false, spec);
	}

	@Override
	public UnlockKey unlockKey() throws DockerException {
		assertApiVersionIsAbove("1.24");
		try {
			final URIResource resource = resource().addPath("swarm").addPath("unlockkey");

			return request(new HttpGet(), UnlockKey.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 500:
				throw new DockerException("server error", e);
			case 503:
				throw new DockerException("node is not part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void unlock(final UnlockKey unlockKey) throws DockerException {
		assertApiVersionIsAbove("1.24");
		try {
			final URIResource resource = resource().addPath("swarm").addPath("unlock");

			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(unlockKey));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 500:
				throw new DockerException("server error", e);
			case 503:
				throw new DockerException("node is not part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ServiceCreateResponse createService(ServiceSpec spec)
			throws DockerException {
		return createService(spec, registryAuthSupplier.authForSwarm());
	}

	@Override
	public ServiceCreateResponse createService(final ServiceSpec spec,
			final RegistryAuth config)
			throws DockerException {
		assertApiVersionIsAbove("1.24");
		final URIResource resource = resource().addPath("services").addPath("create");
		HttpPost post = new HttpPost();
		post.addHeader("X-Registry-Auth", authHeader(config));
		try {
			return request(
					post,
						ServiceCreateResponse.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(spec));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 406:
				throw new DockerException("Server error or node is not part of swarm.", e);
			case 409:
				throw new DockerException("Name conflicts with an existing object.", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public Service inspectService(final String serviceId)
			throws DockerException {
		assertApiVersionIsAbove("1.24");
		try {
			final URIResource resource = resource().addPath("services").addPath(serviceId);
			return request(new HttpGet(), Service.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ServiceNotFoundException(serviceId);
			default:
				throw e;
			}
		}
	}

	@Override
	public void updateService(final String serviceId, final Long version, final ServiceSpec spec)
			throws DockerException {
		updateService(serviceId, version, spec, registryAuthSupplier.authForSwarm());
	}

	@Override
	public void updateService(final String serviceId, final Long version, final ServiceSpec spec,
			final RegistryAuth config)
			throws DockerException {
		assertApiVersionIsAbove("1.24");
		HttpPost post = new HttpPost();
		post.addHeader("X-Registry-Auth", authHeader(config));
		try {
			URIResource resource = resource().addPath("services").addPath(serviceId).addPath("update");
			resource = resource.addParameter("version", String.valueOf(version));
			request(
					post,
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(spec));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ServiceNotFoundException(serviceId);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<Service> listServices() throws DockerException {
		assertApiVersionIsAbove("1.24");
		final URIResource resource = resource().addPath("services");
		return request(new HttpGet(), SERVICE_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Service> listServices(final Service.Criteria criteria)
			throws DockerException {
		assertApiVersionIsAbove("1.24");
		final Map<String, List<String>> filters = new HashMap<>();

		if (criteria.serviceId != null) {
			filters.put("id", Collections.singletonList(criteria.serviceId));
		}
		if (criteria.serviceName != null) {
			filters.put("name", Collections.singletonList(criteria.serviceName));
		}

		final List<String> labels = new ArrayList<>();
		for (Entry<String, String> input : criteria.labels.entrySet()) {
			if ("".equals(input.getValue())) {
				labels.add(input.getKey());
			} else {
				labels.add(String.format("%s=%s", input.getKey(), input.getValue()));
			}
		}

		if (!labels.isEmpty()) {
			filters.put("label", labels);
		}

		URIResource resource = resource().addPath("services");
		resource = resource.addParameter("filters", urlEncodeFilters(filters));
		return request(new HttpGet(), SERVICE_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public void removeService(final String serviceId) throws DockerException {
		assertApiVersionIsAbove("1.24");
		try {
			final URIResource resource = resource().addPath("services").addPath(serviceId);
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ServiceNotFoundException(serviceId);
			default:
				throw e;
			}
		}
	}

	@Override
	public LogStream serviceLogs(String serviceId, LogsParam... params)
			throws DockerException {
		assertApiVersionIsAbove("1.25");
		URIResource resource = noTimeoutResource()
				.addPath("services")
					.addPath(serviceId)
					.addPath("logs");

		for (final LogsParam param : params) {
			resource = resource.addParameter(param.name(), param.value());
		}

		return getServiceLogStream(new HttpGet(), resource, serviceId);
	}

	@Override
	public Task inspectTask(final String taskId) throws DockerException {
		assertApiVersionIsAbove("1.24");
		try {
			final URIResource resource = resource().addPath("tasks").addPath(taskId);
			return request(new HttpGet(), Task.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new TaskNotFoundException(taskId);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<Task> listTasks() throws DockerException {
		assertApiVersionIsAbove("1.24");
		final URIResource resource = resource().addPath("tasks");
		return request(new HttpGet(), TASK_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Task> listTasks(final Task.Criteria criteria)
			throws DockerException {
		assertApiVersionIsAbove("1.24");
		final Map<String, List<String>> filters = new HashMap<>();

		if (criteria.taskId != null) {
			filters.put("id", Collections.singletonList(criteria.taskId));
		}
		if (criteria.taskName != null) {
			filters.put("name", Collections.singletonList(criteria.taskName));
		}
		if (criteria.serviceName != null) {
			filters.put("service", Collections.singletonList(criteria.serviceName));
		}
		if (criteria.nodeId != null) {
			filters.put("node", Collections.singletonList(criteria.nodeId));
		}
		if (criteria.label != null) {
			filters.put("label", Collections.singletonList(criteria.label));
		}
		if (criteria.desiredState != null) {
			filters.put("desired-state", Collections.singletonList(criteria.desiredState));
		}

		URIResource resource = resource().addPath("tasks");
		resource = resource.addParameter("filters", urlEncodeFilters(filters));
		return request(new HttpGet(), TASK_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Config> listConfigs() throws DockerException {
		assertApiVersionIsAbove("1.30");

		final URIResource resource = resource().addPath("configs");

		try {
			return request(new HttpGet(), CONFIG_LIST, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 503:
				throw new NonSwarmNodeException("node is not part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<Config> listConfigs(final Config.Criteria criteria)
			throws DockerException {
		assertApiVersionIsAbove("1.30");

		final Map<String, List<String>> filters = new HashMap<>();

		if (criteria.configId != null) {
			filters.put("id", Collections.singletonList(criteria.configId));
		}
		if (criteria.label != null) {
			filters.put("label", Collections.singletonList(criteria.label));
		}
		if (criteria.name != null) {
			filters.put("name", Collections.singletonList(criteria.name));
		}

		final URIResource resource = resource()
				.addPath("configs")
					.addParameter("filters", urlEncodeFilters(filters));

		try {
			return request(new HttpGet(), CONFIG_LIST, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 503:
				throw new NonSwarmNodeException("node is not part of a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ConfigCreateResponse createConfig(final ConfigSpec config)
			throws DockerException {

		assertApiVersionIsAbove("1.30");
		final URIResource resource = resource().addPath("configs").addPath("create");

		try {
			return request(
					new HttpPost(),
						ConfigCreateResponse.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(config));
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 503:
				throw new NonSwarmNodeException("Server not part of swarm.", ex);
			case 409:
				throw new ConflictException("Name conflicts with an existing object.", ex);
			default:
				throw ex;
			}
		}
	}

	@Override
	public Config inspectConfig(final String configId) throws DockerException {
		assertApiVersionIsAbove("1.30");
		final URIResource resource = resource().addPath("configs").addPath(configId);

		try {
			return request(new HttpGet(), Config.class, resource, ContentType.APPLICATION_JSON);
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 404:
				throw new NotFoundException("Config " + configId + " not found.", ex);
			case 503:
				throw new NonSwarmNodeException("Config not part of swarm.", ex);
			default:
				throw ex;
			}
		}
	}

	@Override
	public void deleteConfig(final String configId) throws DockerException {
		assertApiVersionIsAbove("1.30");
		final URIResource resource = resource().addPath("configs").addPath(configId);

		try {
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 404:
				throw new NotFoundException("Config " + configId + " not found.", ex);
			case 503:
				throw new NonSwarmNodeException("Config not part of a swarm.", ex);
			default:
				throw ex;
			}
		}
	}

	@Override
	public void updateConfig(final String configId, final Long version, final ConfigSpec nodeSpec)
			throws DockerException {

		assertApiVersionIsAbove("1.30");

		final URIResource resource = resource()
				.addPath("configs")
					.addPath(configId)
					.addPath("update");
		resource.addParameter("version", String.valueOf(version));

		try {
			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(nodeSpec));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NotFoundException("Config " + configId + " not found.");
			case 503:
				throw new NonSwarmNodeException("Config not part of a swarm.", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public List<Node> listNodes() throws DockerException {
		assertApiVersionIsAbove("1.24");

		URIResource resource = resource().addPath("nodes");
		return request(new HttpGet(), NODE_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Node> listNodes(Node.Criteria criteria) throws DockerException {
		assertApiVersionIsAbove("1.24");
		final Map<String, List<String>> filters = new HashMap<>();

		if (criteria.nodeId != null) {
			filters.put("id", Collections.singletonList(criteria.nodeId));
		}
		if (criteria.label != null) {
			filters.put("label", Collections.singletonList(criteria.label));
		}
		if (criteria.membership != null) {
			filters.put("membership", Collections.singletonList(criteria.membership));
		}
		if (criteria.nodeName != null) {
			filters.put("name", Collections.singletonList(criteria.nodeName));
		}
		if (criteria.nodeRole != null) {
			filters.put("role", Collections.singletonList(criteria.nodeRole));
		}

		URIResource resource = resource().addPath("nodes");
		resource = resource.addParameter("filters", urlEncodeFilters(filters));
		return request(new HttpGet(), NODE_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public NodeInfo inspectNode(final String nodeId) throws DockerException {
		assertApiVersionIsAbove("1.24");

		URIResource resource = resource()
				.addPath("nodes")
					.addPath(nodeId);

		try {
			return request(new HttpGet(), NodeInfo.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NodeNotFoundException(nodeId);
			case 503:
				throw new NonSwarmNodeException("Node " + nodeId + " is not in a swarm", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void updateNode(final String nodeId, final Long version, final NodeSpec nodeSpec)
			throws DockerException {
		assertApiVersionIsAbove("1.24");

		URIResource resource = resource()
				.addPath("nodes")
					.addPath(nodeId)
					.addPath("update");
		resource.addParameter("version", String.valueOf(version));

		try {
			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(nodeSpec));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NodeNotFoundException(nodeId);
			case 503:
				throw new NonSwarmNodeException("Node " + nodeId + " is not a swarm node", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void deleteNode(final String nodeId) throws DockerException {
		deleteNode(nodeId, false);
	}

	@Override
	public void deleteNode(final String nodeId, final boolean force)
			throws DockerException {
		assertApiVersionIsAbove("1.24");

		final URIResource resource = resource()
				.addPath("nodes")
					.addPath(nodeId)
					.addParameter("force", String.valueOf(force));

		try {
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NodeNotFoundException(nodeId);
			case 503:
				throw new NonSwarmNodeException("Node " + nodeId + " is not a swarm node", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void execResizeTty(final String execId,
			final Integer height,
			final Integer width)
			throws DockerException {
		checkTtyParams(height, width);

		URIResource resource = resource().addPath("exec").addPath(execId).addPath("resize");
		if (height != null && height > 0) {
			resource = resource.addParameter("h", String.valueOf(height));
		}
		if (width != null && width > 0) {
			resource = resource.addParameter("w", String.valueOf(width));
		}

		try {
			request(new HttpPost(), resource, ContentType.TEXT_PLAIN);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ExecNotFoundException(execId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ExecState execInspect(final String execId) throws DockerException {
		final URIResource resource = resource().addPath("exec").addPath(execId).addPath("json");
				
		try {
			return request(new HttpGet(), ExecState.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ExecNotFoundException(execId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public ContainerStats stats(final String containerId)
			throws DockerException {
		final URIResource resource = resource()
				.addPath("containers")
					.addPath(containerId)
					.addPath("stats")
					.addParameter("stream", "0");

		try {
			return request(new HttpGet(), ContainerStats.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void resizeTty(final String containerId, final Integer height, final Integer width)
			throws DockerException {
		checkTtyParams(height, width);

		URIResource resource = resource().addPath("containers").addPath(containerId).addPath("resize");
		if (height != null && height > 0) {
			resource = resource.addParameter("h", String.valueOf(height));
		}
		if (width != null && width > 0) {
			resource = resource.addParameter("w", String.valueOf(width));
		}

		try {
			request(new HttpPost(), resource, ContentType.TEXT_PLAIN);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new ContainerNotFoundException(containerId, e);
			default:
				throw e;
			}
		}
	}

	private void checkTtyParams(final Integer height, final Integer width) throws BadParamException {
		if ((height == null && width == null) || (height != null && height == 0)
				|| (width != null && width == 0)) {
			final Map<String, String> paramMap = Maps.newHashMap();
			paramMap.put("h", height == null ? null : height.toString());
			paramMap.put("w", width == null ? null : width.toString());
			throw new BadParamException(paramMap, "Either width or height must be non-null and > 0");
		}
	}

	@Override
	public List<Network> listNetworks(final ListNetworksParam... params)
			throws DockerException {
		URIResource resource = resource().addPath("networks");
		resource = addParameters(resource, params);
		return request(new HttpGet(), NETWORK_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public Network inspectNetwork(String networkId) throws DockerException {
		final URIResource resource = resource().addPath("networks").addPath(networkId);
		try {
			return request(new HttpGet(), Network.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NetworkNotFoundException(networkId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public NetworkCreation createNetwork(NetworkConfig networkConfig)
			throws DockerException {
		final URIResource resource = resource().addPath("networks").addPath("create");

		try {
			return request(
					new HttpPost(),
						NetworkCreation.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(networkConfig));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NotFoundException("Plugin not found", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void removeNetwork(String networkId) throws DockerException {
		try {
			final URIResource resource = resource().addPath("networks").addPath(networkId);
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new NetworkNotFoundException(networkId, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void connectToNetwork(String containerId, String networkId)
			throws DockerException {
		connectToNetwork(networkId, NetworkConnection.builder().containerId(containerId).build);
	}

	@Override
	public void connectToNetwork(String networkId, NetworkConnection networkConnection)
			throws DockerException {
		final URIResource resource = resource().addPath("networks").addPath(networkId).addPath("connect");

		try {
			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(networkConnection));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				final String message = String.format(
						"Container %s or network %s not found.",
							networkConnection.containerId,
							networkId);
				throw new NotFoundException(message, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void disconnectFromNetwork(String containerId, String networkId)
			throws DockerException {
		disconnectFromNetwork(containerId, networkId, false);
	}

	@Override
	public void disconnectFromNetwork(String containerId, String networkId, boolean force)
			throws DockerException {
		final URIResource resource = resource().addPath("networks").addPath(networkId).addPath("disconnect");

		final Map<String, Object> request = new HashMap<>();
		request.put("Container", containerId);
		request.put("Force", force);

		try {
			request(
					new HttpPost(),
						String.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(request));
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				final String message = String.format(
						"Container %s or network %s not found.",
							containerId,
							networkId);
				throw new NotFoundException(message, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public Volume createVolume() throws DockerException {
		return createVolume(Volume.builder().build);
	}

	@Override
	public Volume createVolume(final Volume volume) throws DockerException {
		final URIResource resource = resource().addPath("volumes").addPath("create");

		return request(
				new HttpPost(),
					Volume.class,
					resource,
					ContentType.APPLICATION_JSON,
					JsonEntity.create(volume));
	}

	@Override
	public Volume inspectVolume(final String volumeName)
			throws DockerException {
		final URIResource resource = resource().addPath("volumes").addPath(volumeName);
		try {
			return request(new HttpGet(), Volume.class, resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new VolumeNotFoundException(volumeName, e);
			default:
				throw e;
			}
		}
	}

	@Override
	public void removeVolume(final Volume volume)
			throws DockerException {
		removeVolume(volume.name);
	}

	@Override
	public void removeVolume(final String volumeName)
			throws DockerException {
		final URIResource resource = resource().addPath("volumes").addPath(volumeName);
		try {
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (DockerRequestException e) {
			switch (e.status()) {
			case 404:
				throw new VolumeNotFoundException(volumeName, e);
			case 409:
				throw new ConflictException("Volume is in use and cannot be removed", e);
			default:
				throw e;
			}
		}
	}

	@Override
	public VolumeList listVolumes(ListVolumesParam... params)
			throws DockerException {
		URIResource resource = resource().addPath("volumes");
		resource = addParameters(resource, params);
		return request(new HttpGet(), VolumeList.class, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public List<Secret> listSecrets() throws DockerException {
		assertApiVersionIsAbove("1.25");
		final URIResource resource = resource().addPath("secrets");
		return request(new HttpGet(), SECRET_LIST, resource, ContentType.APPLICATION_JSON);
	}

	@Override
	public SecretCreateResponse createSecret(final SecretSpec secret)
			throws DockerException {
		assertApiVersionIsAbove("1.25");
		final URIResource resource = resource().addPath("secrets").addPath("create");

		try {
			return request(
					new HttpPost(),
						SecretCreateResponse.class,
						resource,
						ContentType.APPLICATION_JSON,
						JsonEntity.create(secret));
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 406:
				throw new NonSwarmNodeException("Server not part of swarm.", ex);
			case 409:
				throw new ConflictException("Name conflicts with an existing object.", ex);
			default:
				throw ex;
			}
		}
	}

	@Override
	public Secret inspectSecret(final String secretId) throws DockerException {
		assertApiVersionIsAbove("1.25");
		final URIResource resource = resource().addPath("secrets").addPath(secretId);

		try {
			return request(new HttpGet(), Secret.class, resource, ContentType.APPLICATION_JSON);
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 404:
				throw new NotFoundException("Secret " + secretId + " not found.", ex);
			case 406:
				throw new NonSwarmNodeException("Server not part of swarm.", ex);
			default:
				throw ex;
			}
		}
	}

	@Override
	public void deleteSecret(final String secretId) throws DockerException {
		assertApiVersionIsAbove("1.25");
		final URIResource resource = resource().addPath("secrets").addPath(secretId);

		try {
			request(new HttpDelete(), resource, ContentType.APPLICATION_JSON);
		} catch (final DockerRequestException ex) {
			switch (ex.status()) {
			case 404:
				throw new NotFoundException("Secret " + secretId + " not found.", ex);
			default:
				throw ex;
			}
		}
	}

	private URIResource resource() {
		final URIResource target = new URIResource(uri);
		if (!isNullOrEmpty(apiVersion)) {
			return target.addPath(apiVersion);
		}
		return target;
	}

	private URIResource noTimeoutResource() {
		final URIResource target = new URIResource(uri);
		if (!isNullOrEmpty(apiVersion)) {
			return target.addPath(apiVersion);
		}
		return target;
	}

	private static class ResponseTailReader implements Callable<Void> {
		private final ProgressStream stream;
		private final ProgressHandler handler;
		private final HttpRequestBase method;

		public ResponseTailReader(ProgressStream stream, ProgressHandler handler,
				HttpRequestBase method) {
			this.stream = stream;
			this.handler = handler;
			this.method = method;
		}

		@Override
		public Void call() throws DockerException, IOException {
			stream.tail(handler, method.getMethod(), method.getURI());
			return null;
		}
	}

	private void tailResponse(final HttpRequestBase method, final CloseableHttpResponse response,
			final ProgressHandler handler)
			throws DockerException {
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			final ProgressStream stream = new ProgressStream(response.getEntity().getContent());
			final Future<?> future = executor.submit(
					new ResponseTailReader(stream, handler, method));
			future.get();
		} catch (ExecutionException | UnsupportedOperationException | IOException | InterruptedException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof DockerException) {
				throw (DockerException) cause;
			} else {
				throw new DockerException(cause);
			}
		} finally {
			executor.shutdownNow();
			closeQuietly(response);
		}
	}

	private void requestAndTail(final HttpRequestBase method, final ProgressHandler handler,
			final URIResource resource,
			final HttpEntity entity)
			throws DockerException {
		CloseableHttpResponse response = request(method, CloseableHttpResponse.class, resource, entity);
		tailResponse(method, response, handler);
	}
	
	private void requestAndTail(final HttpRequestBase method, final ProgressHandler handler,
			final URIResource resource,
			final ContentType contentType)
			throws DockerException {
		CloseableHttpResponse response = request(method, CloseableHttpResponse.class, resource, contentType);
		tailResponse(method, response, handler);
	}

	private void requestAndTail(final HttpRequestBase method, final ProgressHandler handler,
			final URIResource resource, ContentType contentType,
			final HttpEntity entity)
			throws DockerException {
		CloseableHttpResponse response = request(method, CloseableHttpResponse.class, resource, contentType, entity);
		tailResponse(method, response, handler);
	}

	private void requestAndTail(final HttpRequestBase method, final ProgressHandler handler,
			final URIResource resource)
			throws DockerException {
		CloseableHttpResponse response = request(method, CloseableHttpResponse.class, resource);
		int code = response.getStatusLine().getStatusCode();
		if (code < 200 || code > 299) {
			throw new DockerRequestException(
					method.getMethod(),
						method.getURI(),
						response.getStatusLine(),
						message(response),
						null);
		}
		tailResponse(method, response, handler);
	}

	private HttpRequestBase headers(final HttpRequestBase request) {
		final Set<Map.Entry<String, Object>> entries = headers.entrySet();

		for (final Map.Entry<String, Object> entry : entries) {
			if (entry.getKey() != null && entry.getValue() != null)
				request.addHeader(entry.getKey(), entry.getValue().toString());
		}

		return request;
	}

	private RuntimeException propagate(final HttpRequestBase req,
			final Exception ex)
			throws DockerException {

		if(ex instanceof HttpResponseException) {
			HttpResponseException httpRespEx = ((HttpResponseException) ex);
			throw new DockerRequestException(req.getMethod(), req.getURI(), httpRespEx.getStatusCode(), httpRespEx.getMessage(), ex);
		} else if ((ex instanceof SocketTimeoutException)
				|| (ex instanceof ConnectTimeoutException)) {
			throw new DockerTimeoutException(req.getMethod(), req.getURI(), ex);
		} else if ((ex instanceof InterruptedIOException)
				|| (ex instanceof InterruptedException)) {
			throw new DockerInterruptedException("Interrupted: " + req.getMethod() + " " + req.getURI(), ex);
		} else {
			throw new DockerException(ex);
		}
	}

	private String message(final HttpResponse response) {
		final Readable reader;
		HttpEntity entity = response.getEntity();
		try (InputStream stream = entity.getContent();) {
			reader = new InputStreamReader(entity.getContent(), UTF_8);
			try {
				return CharStreams.toString(reader);
			} catch (IOException ignore) {
				return null;
			}
		} catch (IllegalStateException | UnsupportedOperationException | IOException e) {
			return null;
		}
	}

	private String authHeader(final RegistryAuth registryAuth) throws DockerException {
		// the docker daemon requires that the X-Registry-Auth header is specified
		// with a non-empty string even if your registry doesn't use authentication
		if (registryAuth == null) {
			return "null";
		}
		try {
			return Base64.encodeBase64String(
					ObjectMapperProvider
							.objectMapper()
								.writeValueAsBytes(registryAuth));
		} catch (JsonProcessingException ex) {
			throw new DockerException("Could not encode X-Registry-Auth header", ex);
		}
	}

	private String authRegistryHeader(final RegistryConfigs registryConfigs)
			throws DockerException {
		if (registryConfigs == null) {
			return null;
		}
		try {
			String authRegistryJson = ObjectMapperProvider.objectMapper().writeValueAsString(registryConfigs.configs);

			final String apiVersion = version().apiVersion;
			final int versionComparison = compareVersion(apiVersion, "1.19");

			// Version below 1.19
			if (versionComparison < 0) {
				authRegistryJson = "{\"configs\":" + authRegistryJson + "}";
			} else if (versionComparison == 0) {
				// Version equal 1.19
				authRegistryJson = "{\"auths\":" + authRegistryJson + "}";
			}

			return Base64.encodeBase64String(authRegistryJson.getBytes(StandardCharsets.UTF_8));
		} catch (JsonProcessingException ex) {
			throw new DockerException("Could not encode X-Registry-Config header", ex);
		}
	}

	private void assertApiVersionIsAbove(String minimumVersion)
			throws DockerException {
		final String apiVersion = version().apiVersion;
		final int versionComparison = compareVersion(apiVersion, minimumVersion);

		// Version above minimumVersion
		if (versionComparison < 0) {
			throw new UnsupportedApiVersionException(apiVersion);
		}
	}

	/**
	 * Create a new {@link DefaultDockerClient} builder.
	 *
	 * @return Returns a builder that can be used to further customize and then
	 *         build the client.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Create a new {@link DefaultDockerClient} builder prepopulated with values
	 * loaded from the DOCKER_HOST and DOCKER_CERT_PATH environment variables.
	 *
	 * @return Returns a builder that can be used to further customize and then
	 *         build the client.
	 * @throws DockerCertificateException
	 *           if we could not build a DockerCertificates object
	 */
	public static Builder fromEnv() throws DockerCertificateException {
		final String endpoint = DockerHost.endpointFromEnv();
		final Path dockerCertPath = Paths.get(
				firstNonNull(
						DockerHost.certPathFromEnv(),
							DockerHost.defaultCertPath()));

		final Builder builder = new Builder();

		final Optional<DockerCertificatesStore> certs = DockerCertificates
				.builder()
					.dockerCertPath(dockerCertPath)
					.build();

		if (endpoint.startsWith(UNIX_SCHEME + "://")) {
			builder.uri(endpoint);
		} else if (endpoint.startsWith(NPIPE_SCHEME + "://")) {
			builder.uri(endpoint);
		} else {
			final String stripped = endpoint.replaceAll(".*://", "");
			final HostAndPort hostAndPort = HostAndPort.fromString(stripped);
			final String hostText = hostAndPort.getHost();
			final String scheme = certs.isPresent() ? "https" : "http";

			final int port = hostAndPort.getPortOrDefault(DockerHost.defaultPort());
			final String address = isNullOrEmpty(hostText) ? DockerHost.defaultAddress() : hostText;

			builder.uri(scheme + "://" + address + ":" + port);
		}

		if (certs.isPresent()) {
			builder.dockerCertificates(certs.get());
		}

		return builder;
	}

	public static class Builder {

		public static final String ERROR_MESSAGE = "LOGIC ERROR: DefaultDockerClient does not support being built "
				+ "with both `registryAuth` and `registryAuthSupplier`. "
				+ "Please build with at most one of these options.";
		private URI uri;
		private String apiVersion;
		private long connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;
		private long readTimeoutMillis = DEFAULT_READ_TIMEOUT_MILLIS;
		private int connectionPoolSize = DEFAULT_CONNECTION_POOL_SIZE;
		private DockerCertificatesStore dockerCertificatesStore;
		private boolean dockerAuth;
		private boolean useProxy = true;
		private RegistryAuth registryAuth;
		private RegistryAuthSupplier registryAuthSupplier;
		private Map<String, Object> headers = new HashMap<>();
		private Boolean requestEntityChunked;

		public URI uri() {
			return uri;
		}

		public Builder uri(final URI uri) {
			this.uri = uri;
			return this;
		}

		/**
		 * Set the URI for connections to Docker.
		 *
		 * @param uri
		 *          URI String for connections to Docker
		 * @return Builder
		 */
		public Builder uri(final String uri) {
			return uri(URI.create(uri));
		}

		/**
		 * Set the Docker API version that will be used in the HTTP requests to
		 * Docker daemon.
		 *
		 * @param apiVersion
		 *          String for Docker API version
		 * @return Builder
		 */
		public Builder apiVersion(final String apiVersion) {
			this.apiVersion = apiVersion;
			return this;
		}

		public String apiVersion() {
			return apiVersion;
		}

		public long connectTimeoutMillis() {
			return connectTimeoutMillis;
		}

		/**
		 * Set the timeout in milliseconds until a connection to Docker is
		 * established. A timeout value of zero is interpreted as an infinite
		 * timeout.
		 *
		 * @param connectTimeoutMillis
		 *          connection timeout to Docker daemon in milliseconds
		 * @return Builder
		 */
		public Builder connectTimeoutMillis(final long connectTimeoutMillis) {
			this.connectTimeoutMillis = connectTimeoutMillis;
			return this;
		}

		public long readTimeoutMillis() {
			return readTimeoutMillis;
		}

		/**
		 * Set the SO_TIMEOUT in milliseconds. This is the maximum period of
		 * inactivity between receiving two consecutive data packets from Docker.
		 *
		 * @param readTimeoutMillis
		 *          read timeout to Docker daemon in milliseconds
		 * @return Builder
		 */
		public Builder readTimeoutMillis(final long readTimeoutMillis) {
			this.readTimeoutMillis = readTimeoutMillis;
			return this;
		}

		public DockerCertificatesStore dockerCertificates() {
			return dockerCertificatesStore;
		}

		/**
		 * Provide certificates to secure the connection to Docker.
		 *
		 * @param dockerCertificatesStore
		 *          DockerCertificatesStore object
		 * @return Builder
		 */
		public Builder dockerCertificates(final DockerCertificatesStore dockerCertificatesStore) {
			this.dockerCertificatesStore = dockerCertificatesStore;
			return this;
		}

		public int connectionPoolSize() {
			return connectionPoolSize;
		}

		/**
		 * Set the size of the connection pool for connections to Docker. Note that
		 * due to a known issue, DefaultDockerClient maintains two separate
		 * connection pools, each of which is capped at this size. Therefore, the
		 * maximum number of concurrent connections to Docker may be up to 2 *
		 * connectionPoolSize.
		 *
		 * @param connectionPoolSize
		 *          connection pool size
		 * @return Builder
		 */
		public Builder connectionPoolSize(final int connectionPoolSize) {
			this.connectionPoolSize = connectionPoolSize;
			return this;
		}

		public boolean dockerAuth() {
			return dockerAuth;
		}

		/**
		 * Allows reusing Docker auth info.
		 *
		 * @param dockerAuth
		 *          tells if Docker auth info should be used
		 * @return Builder
		 * @deprecated in favor of
		 *             {@link #registryAuthSupplier(RegistryAuthSupplier)}
		 */
		@Deprecated
		public Builder dockerAuth(final boolean dockerAuth) {
			this.dockerAuth = dockerAuth;
			return this;
		}

		public boolean useProxy() {
			return useProxy;
		}

		/**
		 * Allows connecting to Docker Daemon using HTTP proxy.
		 *
		 * @param useProxy
		 *          tells if Docker Client has to connect to docker daemon using
		 *          HTTP Proxy
		 * @return Builder
		 */
		public Builder useProxy(final boolean useProxy) {
			this.useProxy = useProxy;
			return this;
		}

		public RegistryAuth registryAuth() {
			return registryAuth;
		}

		/**
		 * Set the auth parameters for pull/push requests from/to private
		 * repositories.
		 *
		 * @param registryAuth
		 *          RegistryAuth object
		 * @return Builder
		 *
		 * @deprecated in favor of
		 *             {@link #registryAuthSupplier(RegistryAuthSupplier)}
		 */
		@Deprecated
		public Builder registryAuth(final RegistryAuth registryAuth) {
			if (this.registryAuthSupplier != null) {
				throw new IllegalStateException(ERROR_MESSAGE);
			}
			this.registryAuth = registryAuth;

			// stuff the static RegistryAuth into a RegistryConfigs instance to maintain what
			// DefaultDockerClient used to do with the RegistryAuth before we introduced the
			// RegistryAuthSupplier
			final RegistryConfigs configs = RegistryConfigs.create(
					singletonMap(
							MoreObjects.firstNonNull(registryAuth.serverAddress, ""),
								registryAuth));

			this.registryAuthSupplier = new FixedRegistryAuthSupplier(registryAuth, configs);
			return this;
		}

		public Builder registryAuthSupplier(final RegistryAuthSupplier registryAuthSupplier) {
			if (this.registryAuthSupplier != null) {
				throw new IllegalStateException(ERROR_MESSAGE);
			}
			this.registryAuthSupplier = registryAuthSupplier;
			return this;
		}

		/**
		 * Adds additional headers to be sent in all requests to the Docker Remote
		 * API.
		 */
		public Builder header(String name, Object value) {
			headers.put(name, value);
			return this;
		}

		public Map<String, Object> headers() {
			return headers;
		}

		/**
		 * Allows setting transfer encoding. CHUNKED does not send the
		 * content-length header while BUFFERED does.
		 * 
		 * <p>
		 * By default ApacheConnectorProvider uses CHUNKED mode. Some Docker API
		 * end-points seems to fail when no content-length is specified but a body
		 * is sent.
		 * 
		 * @param requestEntityProcessing
		 *          is the requested entity processing to use when calling docker
		 *          daemon (tcp protocol).
		 * @return Builder
		 */
		public Builder requestEntityChunked(
				final Boolean requestEntityChunked) {
			this.requestEntityChunked = requestEntityChunked;
			return this;
		}

		public Boolean getRequestEntityProcessing() {
			return this.requestEntityChunked;
		}

		public DefaultDockerClient build() {
			// read the docker config file for auth info if nothing else was specified
			if (registryAuthSupplier == null) {
				registryAuthSupplier(new ConfigFileRegistryAuthSupplier());
			}

			return new DefaultDockerClient(this);
		}
	}

	public static void closeQuietly(final Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (final IOException ioe) {
			// ignore
		}
	}

}
