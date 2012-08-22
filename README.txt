What is it?

A way of locking down a software module harder than ever before. 

Motivation:

Current authentication for software use seems to be based on the idea that users authenticate at start up and then
use the software until they either log out or shutdown. In the basic sense it means that once a user is in they
can do what ever they want. 

It also means that if attackers are able to bypass the initial security they can then act with impunity within
the software. 

The aim of this project is to make software modules that are for intents and purspose unusable unless the 
appropriate autentication details are provided. Without them, sections of the code cannot physicall be called,
and their results cannot be used. 

There a few key examples that might server to explain why this is useful:

* P11 Devices: Current cryptography token devices used for key and certificate management require 
		are hardened in a number of ways, however, they still require that user provides
		some of level of authentication to the device after which they can retrieve information.

		This can be exploited even when the user does not have the correct authencitation details, 
		most notably in the padding oracle attack against RSA SID800 and other P11 devices using
		PKCS1.5 for RSA key store. This attack was able to decrypt encrypted information by calling
		the decryption method with CCA data. 

		The aim of this project to ensure that the attackers can't even call the decryption method
		because they literally won't know what it's called unless they have credentials.  

* Virtualisation:
		This may also be applicable in a situation where a server running critical and sensitive 
		services is housed in a virtualised environment. This server may not be public facing and
		is only accessed by other servers which handle authentication.

		However, if the front facing servers are breached, in some cases these internal servers may
		now be vulnerable using the access provided by these front facing servers. 

		As above, if the method of accessing these servers is encrypted and only accessible at run time
		when appropriate credentials have been supplied, this risk is mitigated. 


There also may be some use cases for DRM. 		

Approach:

The aim is to use a shared secret key to provide encrypt the names of all public methods in the API. 

The shared secret key would be based on a threshold scheme such as Shamir's Secret Sharing. The threshold would
be set (at least) 2. 
a) The shared secret would be used to encrypt all public methods in the module prior to Signing and other
   obfuscation (as long as this doesn't alter public method names). 
b) 1 of the secrets is embedded in the encrypted module and accessible via a public method that is plain text 
   (getPackageKey)
c) Each customer or client application is provide with a secret key. This on it's own is not enough to decrypt the 
   modules method names. 
d) The client applications which call the encrypted module will implement the same interface as the encrypted 
   module with the method names in plain text. The client application will be initialised with the customer 
   shared secret. 
c) At initialisation time, the client application will combine the customer shared secret with the encrypted 
   modules secret and use the combined secret to encrypt all the method names creating a look up table 
   between plain text and encrypted method name. 
d) In the client application, each implementation of a method will now call the corresponding encrypted modules 
   method using reflection and the lookup table. 