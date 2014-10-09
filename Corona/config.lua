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
			key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsfoZaCQyMpVXY3RMU23D/lsxlgjeyAaSWLeh9F1dF5A8bykJc2vXXcwagMfLRuCvZLdZvnC2KJAa4tTFjC2eRDoF0ve9LM5eBqAcTPhzSm4eVi8tvTnNPJKsnrM7gktl4QLN8wom6wEKTwsz8D1ePOCB/RmbbYbbRkKaAZvAeadlGuOhF+vATfc3jN4jun1TxTrkWKGCBlS+z4l5eV8xF0F97iX1S5P4B4oU2LkbcUEwLonW4o6et6xMxK04uHiZ0kearv1f5j7Q/w47W21g03leXEevjzwVqy5Xutu6DNCcvTilYLveTflUyKBEl1qaoz90gf9iONEC5TesYGMWtwIDAQAB",
			-- This is optional, it can be strict or serverManaged(default).
			-- stric WILL NOT cache the server response so if there is no network access then it will always return false.
			-- serverManaged WILL cache the server response so if there is no network access it can use the cached response.
			policy = "serverManaged", 
		},
	},
}