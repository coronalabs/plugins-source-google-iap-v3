application = 
{
	content = 
	{ 
		width = 320,
		height = 480,
		scale = "letterbox",
		fps = 60,
	},
	license =
	{
		google =
		{
			key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiXRZV9v8zV2fur7oEbkGs3VTP31ZISFbfG9u7Ui/p2Zq/+wzOvwJUL9jpl4Jx9ErHsFvgDSbP3REdviXetHTaz/rf5MRt/2mXS3DFYZvdk341kB+bXZnfzHwlDhfqT0mxF1VpVHq9zTBMm52R37fL6j+rU+MH2dS55zG578OS4NXOZ1IVbldltWJ7bBk39wzFwD3K+fNkF5pSsSCTLTeJ2MlkIOLP+HxTUVneSf0U40SBsZZSkbywavft9PAdMRnthzTeWSb5pnLN8l6v9lOq7hrYnBWd9oig+fOkIiNnT0DoW5prBLYtynExiw7BDLH1wJHnMyZfXjeLKVEkVxTwIDAQAB",
			-- This is optional, it can be strict or serverManaged(default).
			-- stric WILL NOT cache the server response so if there is no network access then it will always return false.
			-- serverManaged WILL cache the server response so if there is no network access it can use the cached response.
			policy = "serverManaged", 
		},
	},
}