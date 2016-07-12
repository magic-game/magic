using UnityEngine;
using System.Collections;
using MagicEngine;
using System.Collections.Generic;
using AssemblyCSharp;

public class EnemyController : MonoBehaviour, MagicEntity {

    Transform player;               // Reference to the player's position.
    //PlayerHealth playerHealth;      // Reference to the player's health.
   // EnemyHealth enemyHealth;        // Reference to this enemy's health.
    NavMeshAgent nav;               // Reference to the nav mesh agent.
   
	Rigidbody rb;
	private List<SpellEventListener> listeners;
	int health;

	// Use this for initialization
	void Start ()
	{
		health = 1;
		rb = GetComponent<Rigidbody> ();
		listeners = new List<SpellEventListener> ();
	}

    void Awake()
    {
        // Set up the references.
        player = GameObject.FindGameObjectWithTag("Player").transform;
       // playerHealth = player.GetComponent<PlayerHealth>();
       // enemyHealth = GetComponent<EnemyHealth>();
        nav = GetComponent<NavMeshAgent>();
    }


    void Update()
    {
        nav.SetDestination(player.position);
        nav.speed = 3 + ((Mathf.Sin(Time.time * 2.2f) * 2));
		if (health <= 0) {
			Destroy (gameObject);
		}
    }

	public Transform getTransform() {
		return this.transform;
	}

	public Rigidbody getRigidbody() {
		return rb;
	}

	void OnCollisionEnter (Collision col) {
		MagicEntity ent = col.gameObject.GetComponent<MagicEntity> ();
		this.sendEvent (SpellEventType.Collision, ent);
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
		Debug.Log ("took damage!");
		health = health - amount;
	}
}
