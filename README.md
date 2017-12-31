# ContactCongress
Clean MVP structured app with modern libraries


# Depreciated API
The API was hosted by the Sunlight Foundation and it is no longer available, it is available in part with the help of ProPublica. Here's the dev docs related to the change. This repo will remain online but only as an example for the libraries used and the way it was architected.

### Introduction
> Welcome, former Sunlight Congress API users! We want you to be able to use the ProPublica Congress API for your projects, and are here to try and help make the transition process easier. ProPublica took over the Sunlight Congress API in Nov. 2016 and was sun-setted on Oct. 1, 2017. In between, we’ve added support for many Sunlight Congress API features to the ProPublica Congress API. We post about that work on our Nerd Blog and offer a changelog of updates as well.

### Important Things To Know
> The ProPublica Congress differs from the Sunlight Congress API in several important respects:

> The ProPublica Congress API does not offer a location-based lookup service. We recommend that developers use the Google > Civic Information API to accomplish this task. Operating your own geography-based service is another option.
> The ProPublica Congress API does not offer an endpoint to retrieve an image of a member of Congress. Instead, we recommend that developers use the images provided by the United States project on GitHub.
> The ProPublica Congress API does not offer a filtering syntax on requests in the way that the Sunlight Congress API did, instead offering a more REST-ful style with fewer querystring options.
> Authentication for the ProPublica Congress API is by key, but the key is sent as a custom header with requests, not as a query string argument.
> The ProPublica Congress API does not support the experimental RSS format output that the Sunlight Congress API did.
> Developers and users can file error reports or ask questions on GitHub or via email at apihelp@propublica.org.
