using UnityEngine;
using System.Collections;
using MagicEngine;
using AssemblyCSharp;
using System.Collections.Generic;
using UnityStandardAssets.Characters.FirstPerson;

public class BallFabScript : MonoBehaviour, MagicEntity
{

	Rigidbody rb;
	private List<SpellEventListener> listeners;
	int health;
	public GameObject explosionPrefab;
	private float ttl;
	private bool dying = false;
	private float dyingTimer = 3.0f;
	private Light light;
	private Transform quad;
	private Transform sphere;

	// Use this for initialization
	void Start ()
	{
		health = 1;
		rb = GetComponent<Rigidbody> ();
		listeners = new List<SpellEventListener> ();
		light = GetComponentInChildren<Light> ();
		quad = transform.GetChild (1);
		sphere = transform.GetChild (2);
	}
	
	// Update is called once per frame
	void Update ()
	{
		transform.LookAt(Camera.main.transform.position, Vector3.up);
		ttl = ttl - Time.deltaTime;
		if (ttl < 0) {
			health = 0;
		}
		if (health <= 0) {
			//Instantiate (explosionPrefab, transform.position, Quaternion.identity);
			dying = true;
			//Destroy (gameObject);
		}
		if (dying) {
			dyingTimer = dyingTimer - Time.deltaTime;
			if (light != null) {
				light.intensity = 3.0f * dyingTimer;
			}
			if (quad != null) {
				quad.localScale -= new Vector3(Time.deltaTime, Time.deltaTime, Time.deltaTime);
			}
			if (sphere != null) {
				float ratio = Time.deltaTime / 6.0f;
				sphere.localScale -= new Vector3(ratio, ratio, ratio);
			}
			if (dyingTimer < 0) {
				Destroy (gameObject);
			}
		}
	}

	public Place getPlace() {
		return new Place(this.transform);
	}

	public Rigidbody getRigidbody() {
		return rb;
	}

	void OnCollisionEnter (Collision col) {
		MagicEntity ent = col.gameObject.GetComponent<EnemyController> ();
		if (ent != null) {
			this.sendEvent (SpellEventType.Collision, ent);
		}
		MagicEntity platerent = col.gameObject.GetComponent<FirstPersonController > ();
		if (platerent != null) {
			this.sendEvent (SpellEventType.Collision, platerent);
		}
		takeDamage (1);
	}

	public void AddEventListener(SpellEventListener listener) {
		listeners.Add (listener);
	}

	private void sendEvent(SpellEventType type, MagicEntity entity) {
		foreach (SpellEventListener listener in listeners) {
			listener.HandleEvent (type, entity);
		}
	}

	public void takeDamage(int amount) {
		health = health - amount;
	}

	public void setTimeToLive(float ttl) {
		this.ttl = ttl;
	}
}

