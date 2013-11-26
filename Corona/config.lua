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
			key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhPZxAJU0PCihjUziGtGzlC1JgmLqAP/sa77LYLjW7wq7783mPwBLlmJSWUJJraWk5Z9yjbyDLpLuraUudVzI6rU3o7IClgBu+iyigRzlFP3Io5CzdJ4oaann+98GFS/p2HW70r5NAhk7wSd1wDSiqvpAe4YytP/ujt/+ffh352N0c7m7zFWH27JSLj7lkHJa7mmZfGmm49esk2gSR2amMqsmcv5YMOLSySgq9E6Uq1cvPhPa2xbY5M+uNIh7Xm5Ep1GfdfrktEU7Joip9KEqsK91H7H+OKw0VVSfnOFbQefqJfBN1nvGZ7z33BNR0aizuuKoEu72cf2OEvSXlBf3UQIDAQAB",
			-- This is optional, it can be strict or serverManaged(default).
			-- stric WILL NOT cache the server response so if there is no network access then it will always return false.
			-- serverManaged WILL cache the server response so if there is no network access it can use the cached response.
			policy = "serverManaged", 
		},
	},
}